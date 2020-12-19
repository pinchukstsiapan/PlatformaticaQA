package runner;

import runner.type.Profile;
import runner.type.ProfileType;
import runner.type.Run;
import runner.type.RunType;

import java.lang.reflect.Method;

public abstract class TestUtils {

    public static RunType getRunType(Object object) {
        Run run = object.getClass().getAnnotation(Run.class);
        if (run == null) {
            return RunType.Single;
        }

        return run.run();
    }

    public static ProfileType getProfileType(Object object, ProfileType defaultType) {
        Profile profile;
        if (object instanceof Method) {
            profile = ((Method)object).getAnnotation(Profile.class);
        } else {
            profile = object.getClass().getAnnotation(Profile.class);
        }

        if (profile == null) {
            return defaultType;
        }

        return profile.profile();
    }

}
