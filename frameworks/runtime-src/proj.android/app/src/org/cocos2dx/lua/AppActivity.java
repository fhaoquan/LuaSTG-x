/****************************************************************************
 Copyright (c) 2008-2010 Ricardo Quesada
 Copyright (c) 2010-2016 cocos2d-x.org
 Copyright (c) 2013-2016 Chukong Technologies Inc.
 Copyright (c) 2017-2018 Xiamen Yaji Software Co., Ltd.

 http://www.cocos2d-x.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.lua;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.GameControllerActivity;

// public class AppActivity extends Cocos2dxActivity{
public class AppActivity extends GameControllerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setEnableVirtualButton(false);
        super.onCreate(savedInstanceState);
        // Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        if (!isTaskRoot()) {
            // Android launched another instance of the root activity into an existing task
            //  so just quietly finish and go away, dropping the user back into the activity
            //  at the top of the stack (ie: the last state of this task)
            // Don't need to finish it again since it's finished in super.onCreate .
            return;
        }

        // DO OTHER INITIALIZATION BELOW
        // Manually specify an adapter.
//        this.connectController(DRIVERTYPE_NIBIRU);
        // Nibiru SDK have already integrated with MOGA service.
        //this.connectController(DRIVERTYPE_MOGA);
//        this.connectController(DRIVERTYPE_OUYA);

        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static String getSDCardPath() {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist)
            return Environment.getExternalStorageDirectory().toString();
        else
            return "";
    }

    public static void setActivityOrientation(int orientation) {
        sContext.setRequestedOrientation(orientation);
    }

    public static int getActivityOrientation() {
        return sContext.getRequestedOrientation();
    }

    public static boolean getPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (sContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                if (sContext.shouldShowRequestPermissionRationale(permission)) {
                    // rejected
                    return false;
                } else {
                    // not rejected, request
                    sContext.requestPermissions(new String[]{permission}, 1);
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
