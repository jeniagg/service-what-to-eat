package what.to.eat.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.dtos.BasicUserDto;
import what.to.eat.dtos.UserDto;
import what.to.eat.entities.Users;
import what.to.eat.repositories.UsersRepository;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Service
@Transactional
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    /**
     * Get all existing users
     * @return list with existing users
     */
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    /**
     * Get specific user by username
     * @param username - username of the searched user
     * @return user
     */
    public Users getUserByUsername(String username) {
        return usersRepository.getUserByUsername(username);
    }

    /***
     * Returns username by the given userId
     *
     * @param id - the given userId
     * @return username if id is present and bigger than 0
     * @return null otherwise
     */
    public String getUsernameById(Integer id) {
        if (id != null && id >= 1) {
            return usersRepository.getUsernamebyId(id);
        }
        return null;
    }

    /***
     * Returns userId by the given username
     *
     * @param username - the given username
     * @return userId if username is not empty or null
     * @return null otherwise
     */
    public Integer getIdByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return usersRepository.getIdByUsername(username);
    }

    /**
     * Checkes whether the specified username is unique
     * @param username - username to be checked
     * @return true if there is no existing user with the specified username
     */
    public boolean isUsernameUnique(String username) {
        return usersRepository.getUserByUsername(username) == null;
    }

    /**
     * Checks whether the specified email is a valid one
     * Rules: smallerCase, upperCase letters a-Z are allowed,
     * 0-9 digits are allowed, @ is a must, _.- are allowed
     * @param email - email to be checked
     * @return true if it is a valid email
     */
    public boolean isEmailValid(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    /**
     * Checked whether the password is a valid one
     * Rules: the two passwords should be the same
     * A password should have at least one smalledCase letter, one upperCaseLetter,
     * a number, a special character, no spaces and to be at least 8 symbols long
     * @param password
     * @param repeatedPassword
     * @return true if the password is a valid one
     */
    public boolean isPasswordValid(String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            return false;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(regex);
    }

    public boolean areCredentialsValid(String username, String password) throws NoSuchAlgorithmException {
        Users existingUser = getUserByUsername(username);
        String existingPassword = existingUser.getPassword();
        byte[] salt = existingUser.getSalt();
        String encryptNewPassword = hashedPassword(password, salt);
        return existingPassword.equals(encryptNewPassword);
    }

    /**
     * Saves new user to the database
     * @param user - user to be saved
     * @return the saved user
     */
    public Users saveUser(Users user) throws NoSuchAlgorithmException {
        byte[] salt = getSalt();
        String encryptedPassword = hashedPassword(user.getPassword(), salt);
        user.setPassword(encryptedPassword);
        user.setSalt(salt);
        return usersRepository.save(user);
    }

    /**
     * Updates an user
     * @param username - of the user to be updated
     * @param password - new password
     * @param email - new email
     */
    public void updateUser(String username, String password, String email) throws NoSuchAlgorithmException {
        byte[] salt = getSalt();
        String encryptedPassword = hashedPassword(password, salt);
        usersRepository.updateUser(username, encryptedPassword, salt, email);
    }

    /**
     * Deletes the given user
     * @param user - user to be deleted
     */
    public void deleteUser(Users user) {
        usersRepository.delete(user);
    }

    /**
     * Encrypt the given password
     * @param password - password to be encrypted
     * @param salt - salt to be used for the encryption
     * @return the encrypted password
     * @throws NoSuchAlgorithmException
     */
    private String hashedPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if(hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Generate salt to be used in the password
     * @return generated salt
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[20];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Transform user from {@link Users} to {@link BasicUserDto}
     * @param user - the user to be transformed
     * @return BasicUserDto - the transformed user
     */
    public BasicUserDto convertToBasicDto(Users user) {
        return new BasicUserDto(user.getUsername(), user.getEmail());
    }

    /**
     * Transform user from {@link UserDto} to {@link Users}
     * @param userDto - the userDto to be transformed
     * @return user - the transformed user
     */
    public Users convertToEntity(UserDto userDto) {
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
