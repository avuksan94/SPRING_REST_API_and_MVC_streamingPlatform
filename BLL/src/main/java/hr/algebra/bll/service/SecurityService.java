package hr.algebra.bll.service;

public interface SecurityService {
    String doBCryptPassEncoding(String plainTextPassword);
    //String hashPassword(String password, String salt);
    //String generateSalt();
    //boolean checkPassword(String plainPassword, String hashedPassword, String salt);

}
