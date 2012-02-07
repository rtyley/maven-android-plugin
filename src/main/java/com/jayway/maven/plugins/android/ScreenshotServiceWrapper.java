package com.jayway.maven.plugins.android;

import static com.jayway.maven.plugins.android.common.DeviceHelper.getDescriptiveName;
import static org.apache.commons.io.FileUtils.forceMkdir;

import com.android.ddmlib.IDevice;
import com.github.rtyley.android.screenshot.paparazzo.OnDemandScreenshotService;
import com.github.rtyley.android.screenshot.paparazzo.processors.AnimatedGifCreator;
import com.github.rtyley.android.screenshot.paparazzo.processors.ImageSaver;
import java.io.File;
import java.io.IOException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

public class ScreenshotServiceWrapper implements DeviceCallback {

    private final DeviceCallback delegate;
    private final File screenshotParentDir;

    public ScreenshotServiceWrapper(DeviceCallback delegate, MavenProject project) {
        this.delegate = delegate;
        screenshotParentDir = new File(project.getBuild().getDirectory(), "screenshots");
        try {
            forceMkdir(screenshotParentDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doWithDevice(final IDevice device) throws MojoExecutionException, MojoFailureException {
        String deviceName = getDescriptiveName(device);

        File deviceScreenshotDir = new File(screenshotParentDir, deviceName);
        File deviceGifFile = new File(screenshotParentDir, deviceName + ".gif");

        OnDemandScreenshotService screenshotService = new OnDemandScreenshotService(device,
                new ImageSaver(deviceScreenshotDir),
                new AnimatedGifCreator(deviceGifFile)
                );

        screenshotService.start();

        delegate.doWithDevice(device);

        screenshotService.finish();
    }
}
