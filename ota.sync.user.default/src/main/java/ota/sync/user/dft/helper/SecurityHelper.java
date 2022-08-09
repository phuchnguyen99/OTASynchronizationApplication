package ota.sync.user.dft.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SecurityHelper class handles hashing password using SHA256 mechanism.
 */
public class SecurityHelper
{
    /** SALT */
    private static final byte[] SALT = "OverTheAirSalt".getBytes();

    /**
     * get hash.
     * @param data data.
     * @return hash code.
     * @throws NoSuchAlgorithmException if operation fails.
     */
    private static byte[] getHash(final byte[] data) throws NoSuchAlgorithmException
    {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    /**
     * get key password hash.
     * @param string input.
     * @return password hash.
     * @throws SecurityException if operation fails.
     */
    public static byte[] getKeyPasswordHash(final String string) throws SecurityException
    {
        try
        {
            final byte[] data = string.getBytes();
            final byte[] buff = new byte[data.length + SALT.length];
            System.arraycopy(data, 0, buff, 0, data.length);
            System.arraycopy(SALT, 0, buff, data.length, SALT.length);
            return getHash(buff);
        }
        catch(final NoSuchAlgorithmException e)
        {
            throw new SecurityException("Unable to encrypt password", e);
        }
    }

}
