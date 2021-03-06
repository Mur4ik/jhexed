/* 
 * Copyright 2014 Igor Maznitsa (http://www.igormaznitsa.com).
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
package com.igormaznitsa.jhexed.swing.editor.ui.dialogs;

import com.igormaznitsa.jhexed.hexmap.HexFieldLayer;
import java.util.*;

/**
 *
 * @author Igor Maznitsa (http://www.igormaznitsa.com)
 */
public class SelectLayersExportData {
  private boolean exportBackgroundImage;
  private boolean exportCellCommentaries;
  private boolean exportHexBorders;
  private final List<LayerExportRecord> layers = new ArrayList<LayerExportRecord>();
  
  public SelectLayersExportData() {
  }

  public boolean isCellCommentariesExport(){
    return this.exportCellCommentaries;
  }
  
  public boolean isBackgroundImageExport() {
    return this.exportBackgroundImage;
  }

  public void setBackgroundImageExport(final boolean flag) {
    this.exportBackgroundImage = flag;
  }

  public void setExportHexBorders(final boolean flag){
    this.exportHexBorders = flag;
  }
  
  public boolean isExportHexBorders(){
    return this.exportHexBorders;
  }
  
  public void setCellCommentariesExport(final boolean flag){
    this.exportCellCommentaries = flag;
  }
  
  public List<LayerExportRecord> getLayers() {
    return this.layers;
  }

  public void addLayer(final boolean export, final HexFieldLayer layer) {
    this.layers.add(new LayerExportRecord(export, layer));
  }
  
}
