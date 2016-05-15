package base.enum_l;

/**
 * enum直接
 */
public enum Operation {
  PLUS   { double eval(double x, double y) { return x + y; }

    @Override
    String get(String id) {
      return "";
    }


  },
  MINUS  { double eval(double x, double y) { return x - y; }
    @Override
    String get(String id) {
      return "";
    }
  },
  TIMES  { double eval(double x, double y) { return x * y; }
    @Override
    String get(String id) {
      return "";
    }
  },
  DIVIDE { double eval(double x, double y) { return x / y; }
    @Override
    String get(String id) {
      return "";
    }
  };
  // Do arithmetic op represented by this constant
  abstract double eval(double x, double y);
  abstract String get(String id);

}
