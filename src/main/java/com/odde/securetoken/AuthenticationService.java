package com.odde.securetoken;

public class AuthenticationService {

    private final RsaTokenDao rsaTokenDao;
    private final MyLog myLog;

    public AuthenticationService(RsaTokenDao rsaTokenDao, MyLog myLog) {
        this.rsaTokenDao = rsaTokenDao;
        this.myLog = myLog;
    }

    public boolean isValid(String account, String password) {
        // 根據 account 取得自訂密碼
        ProfileDao profileDao = new ProfileDao();
        String passwordFromDao = profileDao.getPassword(account);

        // 根據 account 取得 RSA token 目前的亂數
        String randomCode = rsaTokenDao.getRandom(account);

        // 驗證傳入的 password 是否等於自訂密碼 + RSA token亂數
        String validPassword = passwordFromDao + randomCode;
        boolean isValid = password.equals(validPassword);

        if (isValid) {
            return true;
        } else {
            myLog.log(new SalesOrder(){{
                setShopId(100L);
                setCompany("tmall");
            }});
            return false;
        }
    }
}
