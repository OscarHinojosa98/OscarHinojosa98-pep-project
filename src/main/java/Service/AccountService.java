package Service;

import DAO.AccountDao;
import Model.Account;



public class AccountService {
    public AccountDao accountDao;

    public AccountService(){
        accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao){
        this.accountDao = accountDao;
    }
    // Creates acccount
    public Account registerAccount(Account account){
        return accountDao.addAccount(account);
    }

    // Checks if username and password match
    public Account authorizeLogin(Account account){
        return accountDao.verifyAccount(account);
    }
    
}
