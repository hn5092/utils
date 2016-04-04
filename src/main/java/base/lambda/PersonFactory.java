package base.lambda;

import base.lambda.model.TestUser;

interface PersonFactory<P extends TestUser> {
    P create();
}