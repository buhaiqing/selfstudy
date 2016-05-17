import java.util.List;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by buha on 11/18/2015.
 */
public class SampleTest {
  @BeforeSuite
  public void setup() {
    MockitoAnnotations.initMocks(SampleTest.class);
  }

  @Test
  public void test1() {
    String str1 = new String("my test");

    assertThat(str1, is(str1.intern()));
    String str2 = "my test";
    assertThat(str2, is("my test"));
    assertThat(str2, is(notNullValue()));
  }

  @Test(timeOut = 100)
  public void test2() {
    String message = "he saw a racecar";
    StringBuilder rev = new StringBuilder();
    for (int i = message.length() - 1; i >= 0; i--) {
      rev.append(message.charAt(i));
    }
    System.out.println(rev.toString());
  }


  @MockitoAnnotations.Mock
  List<Integer> olist = mock(List.class);

  @Test
  public void test3() {

    when(olist.size()).thenReturn(11);
    assertThat(olist.size(), equalTo(11));
  }
}

