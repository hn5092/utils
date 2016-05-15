package base.enum_l;

/**
 * enum+interface ->状态机
 */
interface Context {
    State state();

    void state(State state);

    boolean XMLComplete = false;
}

class XMLContext implements Context {
    private State stste = States.INIT;
    @Override
    public State state() {
        return stste;
    }
    @Override
    public void state(State state) {
        this.stste = state;
    }
}

interface State {
    /**
     * @return true to keep processing, false to read more data.
     */
    boolean process(Context context);
}
enum States implements State {
    INIT {
        public boolean process(Context context) {
            // read header
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("init..");
            context.state(States.XML);
            return true;
        }
    },
    XML {
        public boolean process(Context context) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            context.state(States.ROOT);
            System.out.println(" begin ROOT");
            return true;
        }
    }, ROOT {
        public boolean process(Context context) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            context.state(States.IN_ROOT);
            System.out.println(" begin IN_ROOT");
            return true;
        }
    }, IN_ROOT {
        public boolean process(Context context) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            context.state(States.IN_ROOT);
            System.out.println(" complete");

            return false;
        }
    };

    public static void main(String[] args) {
        XMLContext xmlContext = new XMLContext();
        while (true) {
            if (!(xmlContext.state().process(xmlContext))) break;
        }
    }
}
//
