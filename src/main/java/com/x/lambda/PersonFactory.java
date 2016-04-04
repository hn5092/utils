package com.x.lambda;

import com.x.lambda.model.TestUser;

interface PersonFactory<P extends TestUser> {
    P create();
}