package ota.sync.user;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.nio.charset.StandardCharsets;

public class User
{
    /** username */
    private final String username;

    /** password */
    private String hashString;

    /**
     * Constructor
     * @param username username.
     * @param hash hash.
     */
    public User(final String username, final byte[] hash)
    {
        this.username = username;
        this.hashString = convertHashToString(hash);
    }

    /**
     * Constructor.
     * @param username username.
     * @param hashString hash string.
     */
    public User(final String username, final String hashString)
    {
        this.username = username;
        this.hashString = hashString;
    }

    /**
     * get user name.
     * @return username.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * get hash string password.
     * @return hash string.
     */
    public String getHashString()
    {
        return hashString;
    }

    /**
     * convert hash to string.
     * @param hash hash.
     * @return hash string.
     */
    private String convertHashToString(final byte[] hash)
    {
        return new String(hash, StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(final Object o)
    {
        if(o == null || o.getClass() != this.getClass())
        {
            return false;
        }
        if(this == o)
        {
            return true;
        }
        final User that = (User) o;
        return new EqualsBuilder().append(this.username, that.username)
                                  .append(this.hashString, that.hashString).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(username).append(hashString).hashCode();
    }
}
