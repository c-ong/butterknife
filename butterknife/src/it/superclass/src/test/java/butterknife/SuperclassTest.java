package butterknife;

import bar.TestOne;
import bar.TestTwo;
import foo.BaseThing;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;

@RunWith(ButterKnifeTestRunner.class)
public class SuperclassTest {
  @Before public void setUp() {
    Views.INJECTORS.clear();
  }

  @Test public void superclassInjection() {
    TestOne target = new TestOne();
    Views.inject(target);

    assertThat(target.thing).isNotNull();
    assertThat(target.baseThing).isNotNull();

    assertThat(Views.INJECTORS).containsKey(TestOne.class);
    assertThat(Views.INJECTORS).doesNotContainKey(BaseThing.class);
  }

  @Test public void onlyParentClassInjection() {
    TestTwo target = new TestTwo();
    Views.inject(target);

    assertThat(target.baseThing).isNotNull();

    assertThat(Views.INJECTORS).containsKey(BaseThing.class);

    Method baseThing = Views.INJECTORS.get(BaseThing.class);
    assertThat(Views.INJECTORS).contains(entry(TestTwo.class, baseThing));
  }
}
