/*
 * Copyright (C) 2009 Jayway AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayway.maven.plugins.android;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.codehaus.plexus.util.ReflectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Excercises the {@link AndroidSdk} class.
 *
 * @author hugo.josefson@jayway.com
 * @author Manfred Moser <manfred@simpligility.com>
 */
public class AndroidSdkTest {
    
    private SdkTestSupport sdkTestSupport; 
    
    @Before
    public void setUp(){
        sdkTestSupport = new SdkTestSupport();
    }

    @Test
    public void givenToolAdbThenPathIsPlatformTools() {
        final String pathForTool =sdkTestSupport.getSdk_with_platform_1_5().getPathForTool("adb");
        Assert.assertEquals(new File(sdkTestSupport.getEnv_ANDROID_HOME() + "/platform-tools").getAbsolutePath(), new File(pathForTool).getParentFile().getAbsolutePath());
    }

    @Test
    public void givenToolAndroidThenPathIsCommon() {
        final String pathForTool =sdkTestSupport.getSdk_with_platform_1_5().getPathForTool("android");
        Assert.assertEquals(new File(sdkTestSupport.getEnv_ANDROID_HOME() + "/tools").getAbsolutePath(), new File(pathForTool).getParentFile().getAbsolutePath());
    }

    @Test
    public void givenToolAaptAndPlatform1dot5ThenPathIsPlatformTools() {
        final AndroidSdk sdk = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "3");
        final String pathForTool = sdk.getPathForTool("aapt");
        Assert.assertEquals(new File(sdkTestSupport.getEnv_ANDROID_HOME() + "/platform-tools"), new File(pathForTool).getParentFile());
    }

    @Test(expected = InvalidSdkException.class)
    public void givenInvalidSdkPathThenException() throws IOException {
        new AndroidSdk(File.createTempFile("android-maven-plugin", "test"), null).getLayout();
    }

    @Test(expected = InvalidSdkException.class)
    public void givenInvalidPlatformStringThenException() throws IOException {
        final AndroidSdk sdk = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "invalidplatform");
    }

    @Test
    public void givenDefaultSdkThenLayoutIs23(){
        Assert.assertEquals(sdkTestSupport.getSdk_with_platform_default().getLayout(), AndroidSdk.Layout.LAYOUT_2_3);
    }

    @Test
    public void givenPlatform1dot5ThenPlatformis1dot5() throws IllegalAccessException {
        final File path = (File) ReflectionUtils.getValueIncludingSuperclasses("sdkPath",sdkTestSupport.getSdk_with_platform_1_5());
        Assert.assertEquals(new File(path, "/platforms/android-3"),sdkTestSupport.getSdk_with_platform_1_5().getPlatform());
    }

    /**
     * Test all available platforms and api level versions. All have to be installed locally
     * for this test to pass including the obsolete ones.
     */
    @Test
    public void validPlatformsAndApiLevels() {
        final AndroidSdk sdk3 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "3");
        final AndroidSdk sdk4 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "4");
        final AndroidSdk sdk7 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "7");
        final AndroidSdk sdk8 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "8");
        final AndroidSdk sdk10 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "10");
        final AndroidSdk sdk11 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "11");
        final AndroidSdk sdk12 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "12");
        final AndroidSdk sdk13 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "13");
        final AndroidSdk sdk14 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "14");

        final AndroidSdk sdk1_5 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "1.5");
        final AndroidSdk sdk1_6 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "1.6");
        final AndroidSdk sdk2_1 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "2.1");
        final AndroidSdk sdk2_2 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "2.2");
        final AndroidSdk sdk2_3 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "2.3.3");
        final AndroidSdk sdk3_0 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "3.0");
        final AndroidSdk sdk3_1 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "3.1");
        final AndroidSdk sdk3_2 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "3.2");
        final AndroidSdk sdk4_0 = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "4.0");
    }

    @Test(expected = InvalidSdkException.class)
    public void invalidPlatformAndApiLevels() {
        final AndroidSdk invalid = new AndroidSdk(new File(sdkTestSupport.getEnv_ANDROID_HOME()), "invalid");

    }


}
