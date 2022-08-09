import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ota.sync.database.cache.UserCacheHelper;
import ota.sync.user.User;
import ota.sync.user.UserService;
import ota.sync.user.dft.DefaultUserService;
import ota.sync.user.dft.helper.SecurityHelper;
import ota.sync.user.exception.UserServiceException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultUserServiceShould
{
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final UserCacheHelper userCacheHelper = context.mock(UserCacheHelper.class);
    private UserService userService;
    final String username = "technician1";

    @Before
    public void setUp()
    {
        userService = new DefaultUserService(userCacheHelper);
    }

    @Test
    public void throw_exception_if_user_already_existed() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(new User("technician1", "Technician11"));
                will(returnValue(new User("technician1", "\"��1�^\\u000FƘb���\\u0010'��-0P��{��\\\"^������\"")));
            }
        });
        exception.expect(UserServiceException.class);
        exception.expectMessage("User already existed.");
        userService.createUser("technician1", "Technician11");
    }

    @Test
    public void throw_exception_if_password_less_than_8_characters() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(with(any(User.class)));
                will(returnValue(null));
            }
        });
        final String password1 = "fail";
        exception.expect(UserServiceException.class);
        exception.expectMessage("Password must contain at least 8 characters.");
        userService.createUser(username, password1);
    }

    @Test
    public void throw_exception_if_password_does_not_contain_at_least_one_number() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(with(any(User.class)));
                will(returnValue(null));
            }
        });
        final String password2 = "technician";
        exception.expect(UserServiceException.class);
        exception.expectMessage("Password must contain at least 1 number.");
        userService.createUser(username, password2);
    }

    @Test
    public void throw_exception_if_password_does_not_contain_at_least_one_uppercase_letter() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(with(any(User.class)));
                will(returnValue(null));
            }
        });
        final String password2 = "technician1";
        exception.expect(UserServiceException.class);
        exception.expectMessage("Password must contain at least 1 uppercase letter.");
        userService.createUser(username, password2);
    }

    @Test
    public void create_user() throws Exception
    {
        final byte[] password = SecurityHelper.getKeyPasswordHash("Technician11");
        final User user = new User("technician1", "Technician11");
        final User addUser = new User("technician1", password);
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(user);
                will(returnValue(null));

                oneOf(userCacheHelper).addUser(addUser);
            }
        });
        final boolean result = userService.createUser("technician1", "Technician11");
        assertThat(result, equalTo(true));
    }

    @Test
    public void throw_exception_if_user_name_does_not_exist_when_login() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(with(any(User.class)));
                will(returnValue(null));
            }
        });
        exception.expectMessage("User does not exist.");
        exception.expect(UserServiceException.class);
        userService.login("username", "Technician11");
    }

    @Test
    public void throw_exception_if_passwords_dont_match() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(with(any(User.class)));
                will(returnValue(new User("technician1", "Technician11")));
            }
        });
        exception.expectMessage("Password is incorrect.");
        exception.expect(UserServiceException.class);
        userService.login("technician1", "Technician12");
    }

    @Test
    public void succeed_log_in_user() throws Exception
    {
        context.checking(new Expectations(){
            {
                oneOf(userCacheHelper).getUser(with(any(User.class)));
                will(returnValue(new User("technician1", "��1�^\u000FƘb���\u0010'��-0P��{��\"^������")));
            }
        });
        userService.login("technician1", "Technician11");
    }
}
