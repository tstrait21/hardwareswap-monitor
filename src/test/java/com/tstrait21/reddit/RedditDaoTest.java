package com.tstrait21.reddit;

import org.junit.Assert;
import org.junit.Test;

public class RedditDaoTest {

    @Test
    public void testInvalidCredentials() {
        RedditDAO dao = new RedditDAO(null, null);

        Assert.assertEquals(false, dao.isTokenValid());

        dao = new RedditDAO("", "");

        Assert.assertEquals(false, dao.isTokenValid());
    }
}
