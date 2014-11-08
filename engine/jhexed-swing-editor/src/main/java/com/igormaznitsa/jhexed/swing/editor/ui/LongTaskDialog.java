/*
 * Copyright 2014 Igor Maznitsa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by  applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.jhexed.swing.editor.ui;

import com.igormaznitsa.jhexed.swing.editor.ui.dialogs.AbstractDialog;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.concurrent.*;
import javax.swing.SwingUtilities;

public class LongTaskDialog extends AbstractDialog {
  private static final long serialVersionUID = -5421216804205052165L;

  private static final ExecutorService executors = Executors.newCachedThreadPool(new ThreadFactory() {

    @Override
    public Thread newThread(final Runnable r) {
      final Thread result = new Thread(r, "LONG_TASK_THREAD"+Math.abs(System.nanoTime()));
      result.setDaemon(true);
      return result;
    }
  });
  
  private volatile Future<?> theThread;
  private final Runnable task;

  public static void cancel(){
    executors.shutdownNow();
  }
  
  public LongTaskDialog(final java.awt.Frame parent, final String taskName, final Runnable task) {
    super(parent, true);
    initComponents();
    this.setTitle(taskName);
    this.task = task;
    this.labelTaskName.setText(taskName);
    this.setLocationRelativeTo(parent);
  }

  public synchronized void startTask(){
    if (theThread == null){
      this.progressBar.setIndeterminate(true);
      theThread = executors.submit(task);
      new Timer("Long_time_task_dialog", true).schedule(new java.util.TimerTask() {
        @Override
        public void run() {
          if (theThread.isCancelled() || theThread.isDone()){
            SwingUtilities.invokeLater(new Runnable(){
              @Override
              public void run() {
                dispose();
              }
            });
            this.cancel();
          }
        }
      }, 500L, 1000L);
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          setVisible(true);
        }
      });
    }
  }
  
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    progressBar = new javax.swing.JProgressBar();
    labelTaskName = new javax.swing.JLabel();
    buttonCancel = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setLocationByPlatform(true);
    setResizable(false);

    labelTaskName.setText("Task title");

    buttonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/igormaznitsa/jhexed/swing/editor/icons/cross.png"))); // NOI18N
    buttonCancel.setText("Cancel");
    buttonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonCancelActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(labelTaskName)
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(buttonCancel)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(labelTaskName)
        .addGap(7, 7, 7)
        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(buttonCancel)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
    if (this.theThread!=null && !(this.theThread.isCancelled() || this.theThread.isDone())){
      this.theThread.cancel(true);
    }
  }//GEN-LAST:event_buttonCancelActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JLabel labelTaskName;
  private javax.swing.JProgressBar progressBar;
  // End of variables declaration//GEN-END:variables

  @Override
  public void processEscape(final ActionEvent e) {
  }
}
