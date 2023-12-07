package edu.northeastern.groupproject_outandabout.data;



/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result {
    public static Error Success;

    // hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

//    @NonNull
//    @Override
//    public String toString() {
//        if (this instanceof Result.Success) {
//            Result.Success success = (Result.Success) this;
//            return "Success[data=" + success.getData().toString() + "]";
//        } else if (this instanceof Result.Error) {
//            Result.Error error = (Result.Error) this;
//            return "Error[exception=" + error.getError().toString() + "]";
//        }
//        return "";
//    }

    // Success sub-class
    public final static class Success<T> extends Result {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends Result {

        public Error() {
        }

    }
}