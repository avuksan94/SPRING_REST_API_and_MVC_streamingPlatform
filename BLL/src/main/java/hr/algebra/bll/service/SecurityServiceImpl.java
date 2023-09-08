package hr.algebra.bll.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class SecurityServiceImpl implements SecurityService {
    private static final String ALGORITHM = "SHA-256";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public SecurityServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String doBCryptPassEncoding(String plainTextPassword) {
        return bCryptPasswordEncoder.encode(plainTextPassword);
    }



    /*
    THIS WAS FOR TESTING
    @Override
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    @Override
    public String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword, String salt) {
        String computedHash = hashPassword(plainPassword, salt);
        return computedHash.equals(hashedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
     */
}
