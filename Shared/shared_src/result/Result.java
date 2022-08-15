package result;

import javax.naming.spi.DirStateFactory;

public class Result {
    public String message;
    public boolean failure;
    public Result(){}
    public Result(boolean x) {
        this.failure = x;
    }
}