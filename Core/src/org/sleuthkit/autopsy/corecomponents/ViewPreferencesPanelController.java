/*
 * Autopsy Forensic Browser
 *
 * Copyright 2018 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.corecomponents;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.sleuthkit.autopsy.coreutils.MessageNotifyUtil;
import java.util.logging.Level;
import org.openide.util.NbBundle.Messages;
import org.sleuthkit.autopsy.coreutils.Logger;

/**
 * Controller for the main settings panel
 */
@OptionsPanelController.TopLevelRegistration(categoryName = "#OptionsCategory_Name_View",
        iconBase = "org/sleuthkit/autopsy/images/view-preferences-32.png",
        position = 2,
        keywords = "#OptionsCategory_Keywords_View",
        keywordsCategory = "View")
public final class ViewPreferencesPanelController extends OptionsPanelController {

    private ViewPreferencesPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;
    private static final Logger logger = Logger.getLogger(ViewPreferencesPanelController.class.getName());

    @Override
    public void update() {
        getPanel().load();
        changed = false;
    }

    @Override
    public void applyChanges() {
        getPanel().store();
        changed = false;
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null;
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        if (pcs.getPropertyChangeListeners().length == 0) {
            pcs.addPropertyChangeListener(l);
        }
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        /**
         * Note the NetBeans Framework does not appear to call this at all. We
         * are using NetBeans 7.3.1 Build 201306052037. Perhaps in a future
         * version of the Framework this will be resolved, but for now, simply
         * don't unregister anything and add one time only in the
         * addPropertyChangeListener() method above.
         */
    }

    /**
     * Retrieve an instance of the ViewPreferencesPanel.
     * 
     * @return A ViewPreferencesPanel instance.
     */
    private ViewPreferencesPanel getPanel() {
        if (panel == null) {
            panel = new ViewPreferencesPanel();
            panel.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                if (evt.getPropertyName().equals(OptionsPanelController.PROP_CHANGED)) {
                    changed();
                }
            });
        }
        return panel;
    }

    /**
     * Executed whenever a property change occurs.
     */
    @Messages({"ViewOptionsController.moduleErr=Error processing value changes.",
                "ViewOptionsController.moduleErr.msg=Value change processing failed."})
    void changed() {
        if (!changed) {
            changed = true;

            try {
                pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error processing property change", ex); //NON-NLS
                MessageNotifyUtil.Notify.show(
                        Bundle.ViewOptionsController_moduleErr(),
                        Bundle.ViewOptionsController_moduleErr_msg(),
                        MessageNotifyUtil.MessageType.ERROR);
            }
        }

        try {
            pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing property change validation.", e); //NON-NLS
            MessageNotifyUtil.Notify.show(
                    Bundle.ViewOptionsController_moduleErr(),
                    Bundle.ViewOptionsController_moduleErr_msg(),
                    MessageNotifyUtil.MessageType.ERROR);
        }
    }
}
