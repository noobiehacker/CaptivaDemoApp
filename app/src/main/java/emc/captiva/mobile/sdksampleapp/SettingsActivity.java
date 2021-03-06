/** -------------------------------------------------------------------------
 * Copyright 2013-2015 EMC Corporation.  All rights reserved.
 ---------------------------------------------------------------------------- */

package emc.captiva.mobile.sdksampleapp;

import emc.captiva.mobile.sdk.CaptureImage;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * This class handles managing all of the global preferences.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    /* (non-Javadoc)
     * @see android.content.SharedPreferences.OnSharedPreferenceChangeListener#onSharedPreferenceChanged(android.content.SharedPreferences, java.lang.String)
     */
    @SuppressWarnings("deprecation")
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

        // Because this handler has preference sets that cause the handler to loop, let's unwire the handler
        // and then wire it back up at the end.
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

        // Make sure our preferences conform to our allowed values.
        Preference p = findPreference(key);
        if (p instanceof EditTextPreference) {
            EditTextPreference pref = (EditTextPreference)p;            
            String temp;
            Integer i;
            Float f;
            if (key.compareToIgnoreCase("GPREF_SENSOR_LIGHT_VALUE") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 50);
                i = Math.max(0, i);
                i = Math.min(5000, i);
                pref.setText(i.toString());
            }
            
            if (key.compareToIgnoreCase("GPREF_SENSOR_MOTION_VALUE") == 0) {
                temp = pref.getText();
                f = CoreHelper.getFloat(temp, .30f);
                f = Math.max(0.0f, f);
                f = Math.min(10.0f, f);
                pref.setText(f.toString());
            }
            
            if (key.compareToIgnoreCase("GPREF_CAPTUREDELAY") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 500);
                i = Math.max(0, i);
                pref.setText(i.toString());
            }

            if (key.compareToIgnoreCase("GPREF_CONTINUOUSCAPTUREFRAMEDELAY") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 500);
                i = Math.max(0, i);
                pref.setText(i.toString());
            }

            if (key.compareToIgnoreCase("GPREF_CAPTURETIMEOUT") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 0);
                i = Math.max(0, i);
                pref.setText(i.toString());
            }

            if (key.compareToIgnoreCase("GPREF_CAPTURESIMILARITY") == 0) {
                temp = pref.getText();
                f = CoreHelper.getFloat(temp, 50f);
                f = Math.max(0, f);
                f = Math.min(f, 100);
                pref.setText(f.toString());
            }

            if (key.compareToIgnoreCase("GPREF_CAPTURECOUNT") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 2);
                i = Math.max(1, i);
                pref.setText(i.toString());
            }

            if (key.compareToIgnoreCase("GPREF_IMAGEFORMAT") == 0) {
                temp = pref.getText();
                if (temp == null || temp.isEmpty()) {
                    pref.setText(CaptureImage.SAVE_JPG);
                } 
                else if (temp.compareToIgnoreCase(CaptureImage.SAVE_JPG) != 0 && 
                		temp.compareToIgnoreCase(CaptureImage.SAVE_PNG) != 0 && 
                		temp.compareToIgnoreCase(CaptureImage.SAVE_TIF) != 0) {
                    pref.setText(CaptureImage.SAVE_JPG);
                }
            }
            
            if (key.compareToIgnoreCase("GPREF_JPGQUALITY") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 95);
                if (i < 0 || i > 100) {
                    pref.setText("95");
                } else {
                    pref.setText(i.toString());
                }
            }
            
            if (key.compareToIgnoreCase("GPREF_DPIX") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 0);
                if (i <= 0) {
                    pref.setText("");
                }                
            }

            if (key.compareToIgnoreCase("GPREF_DPIY") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 0);
                if (i <= 0) {
                    pref.setText("");
                }
            }
            
            if (key.compareToIgnoreCase("GPREF_FILTER_CROP_PADDING") == 0) {
            	temp = pref.getText();
                f = CoreHelper.getFloat(temp, 0.0f);
                f = Math.max(0.0f, f);
                f = Math.min(1.0f, f);
                pref.setText(f.toString());
            }

            if (key.compareToIgnoreCase("GPREF_FILTER_REMOVE_NOISE") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 7);
                if (i <= 0)
                {
                    pref.setText("0");
                }
                else
                {
                    pref.setText(i.toString());
                }
            }

            if (key.compareToIgnoreCase("GPREF_QUAD_CROP_COLOR") == 0) {
                temp = pref.getText();
                try {
                    Color.parseColor(temp);
                    pref.setText(temp);
                } catch (Exception exception) {
                    pref.setText("blue");
                }
            }

            if (key.compareToIgnoreCase("GPREF_QUAD_CROP_LINE_WIDTH") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 4);
                i = Math.max(1, i);
                pref.setText(i.toString());
            }

            if (key.compareToIgnoreCase("GPREF_QUAD_CROP_CIRCLE_RADIUS") == 0) {
                temp = pref.getText();
                i = CoreHelper.getInteger(temp, 24);
                i = Math.max(0, i);
                pref.setText(i.toString());
            }

            if (key.compareToIgnoreCase("GPREF_APPLICATIONID") == 0 ||
            	key.compareToIgnoreCase("GPREF_LICENSE") == 0) {
            	CoreHelper.license(this);
            }

            if (key.compareToIgnoreCase("GPREF_PICTURE_CROP_COLOR") == 0) {
                temp = pref.getText();
                try {
                    Color.parseColor(temp);
                    pref.setText(temp);
                } catch (Exception exception) {
                    pref.setText("green");
                }
            }

            if (key.compareToIgnoreCase("GPREF_PICTURE_CROP_WARNING_COLOR") == 0) {
                temp = pref.getText();
                try {
                    Color.parseColor(temp);
                    pref.setText(temp);
                } catch (Exception exception) {
                    pref.setText("red");
                }
            }

            if (key.compareToIgnoreCase("GPREF_PICTURE_CROP_ASPECT_WIDTH") == 0) {
                temp = pref.getText();
                float aspectWidthDefault = 8.5f;
                f = CoreHelper.getFloat(temp, aspectWidthDefault);
                // The width must be a positive value, set to default if not.
                if (f <= 0) {
                    f = aspectWidthDefault;
                }
                pref.setText(f.toString());
            }

            if (key.compareToIgnoreCase("GPREF_PICTURE_CROP_ASPECT_HEIGHT") == 0) {
                temp = pref.getText();
                float aspectHeightDefault = 11f;
                f = CoreHelper.getFloat(temp, aspectHeightDefault);
                // The height must be a positive value, set to default if not.
                if (f <= 0) {
                    f = aspectHeightDefault;
                }
                pref.setText(f.toString());
            }
        }

        // Wiring the preference changed handler back up.
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    
    /* (non-Javadoc)
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        addPreferencesFromResource(R.xml.preferences);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        
        // Listen for changes.
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onPause() {
        // Cancel listening for changes.
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

        // Now that our work is done, call the superclass onPause();
        super.onPause();
    }
}
