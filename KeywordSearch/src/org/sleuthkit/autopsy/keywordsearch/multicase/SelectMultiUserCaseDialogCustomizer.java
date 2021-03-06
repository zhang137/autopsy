/*
 * Autopsy Forensic Browser
 *
 * Copyright 2019 Basis Technology Corp.
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
package org.sleuthkit.autopsy.keywordsearch.multicase;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.sleuthkit.autopsy.casemodule.multiusercases.CaseNodeData;
import org.sleuthkit.autopsy.casemodule.multiusercasesbrowser.MultiUserCaseBrowserCustomizer;

/**
 * Customizer for SelectMultiUserCasesPanel. Displays the 'Create date' and
 * 'Directory' columns
 */
public class SelectMultiUserCaseDialogCustomizer implements MultiUserCaseBrowserCustomizer {

    @Override
    public List<Column> getColumns() {
        List<Column> properties = new ArrayList<>();
        properties.add(Column.CREATE_DATE);
        properties.add(Column.DIRECTORY);
        return properties;
    }

    @Override
    public List<SortColumn> getSortColumns() {
        List<SortColumn> sortColumns = new ArrayList<>();
        sortColumns.add(new SortColumn(Column.CREATE_DATE, false, 1));
        return sortColumns;
    }

    @Override
    public boolean allowMultiSelect() {
        return true;
    }

    @Override
    public List<Action> getActions(CaseNodeData nodeData) {
        return new ArrayList<>();
    }

    @Override
    public Action getPreferredAction(CaseNodeData nodeData) {
        return null;
    }

}
