/*
 * WorldManagement.java
 *
 * Created on 21/11/2010, 14:00:47
 */
package world.view;

import genetic.EnvironmentSerializable;
import genetic.view.EnvironmentDetail;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import world.EnvironmentInformationQueryer;
import world.MonitoredEnvironment;

/**
 * @todo open a XMPP connection to enable queries
 * @author romulo
 */
public class WorldManagement extends javax.swing.JFrame {

    private ArrayList<MonitoredEnvironment> monitoredEnvironments = new ArrayList<MonitoredEnvironment>();
    private String[] columnNames = new String[]{"Ecossistema", "Indivíduos"};
    private EnvironmentInformationQueryer queryer;

    /** Creates new form WorldManagement */
    public WorldManagement() {
        initComponents();
        populateTable();

        queryer = new EnvironmentInformationQueryer(this);
        Thread t = new Thread(queryer);
        t.start();
    }

    /**
     * Rewtrieves the list of monitored environments
     * @return
     */
    public ArrayList<MonitoredEnvironment> getMonitoredEnvironments() {
        return monitoredEnvironments;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelEnvironments = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEnvironments = new javax.swing.JTable();
        jButtonDetail = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButtonAddEnvironment = new javax.swing.JButton();
        jButtonGlobalData = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelEnvironments.setText("Ecossistemas:");

        jTableEnvironments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ecossistema", "Indivíduos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableEnvironments);

        jButtonDetail.setText("Detalhes");
        jButtonDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetailActionPerformed(evt);
            }
        });

        jButtonRemove.setText("Remover");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        jButtonAddEnvironment.setText("Adicionar ecossistema");
        jButtonAddEnvironment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEnvironmentActionPerformed(evt);
            }
        });

        jButtonGlobalData.setText("Dados globais");
        jButtonGlobalData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGlobalDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEnvironments)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonDetail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonRemove))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAddEnvironment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonGlobalData)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelEnvironments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDetail)
                    .addComponent(jButtonRemove))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddEnvironment)
                    .addComponent(jButtonGlobalData))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetailActionPerformed
        int[] rows = jTableEnvironments.getSelectedRows();
        if (rows.length == 1) {
            String user = getUserFromTableRow(rows[0]);
            MonitoredEnvironment selected = getEnvironmentByName(user);
            if(selected != null) {
                EnvironmentDetail detail = new EnvironmentDetail(selected.getIndividualCount(), selected.getFoodCount());
                detail.setVisible(true);
            }
        }
        populateTable();
    }//GEN-LAST:event_jButtonDetailActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        int[] rows = jTableEnvironments.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            removeEnvironmentByName(getUserFromTableRow(rows[i]));
        }
        populateTable();
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jButtonAddEnvironmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEnvironmentActionPerformed
        String identifier = inputConnectionUser();
        if (identifier != null && !identifier.equals("")) {
            monitoredEnvironments.add(new MonitoredEnvironment(identifier));
        }
        populateTable();
    }//GEN-LAST:event_jButtonAddEnvironmentActionPerformed

    private void jButtonGlobalDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGlobalDataActionPerformed
        GlobalDataView gd = new GlobalDataView();
        gd.setVisible(true);
        int environments = 0, individuals = 0, foods = 0;
        for (MonitoredEnvironment env : monitoredEnvironments) {
            foods += env.getFoodCount();
            individuals += env.getIndividualCount();
            environments++;
        }
        gd.updateStatistcs(environments, foods, individuals);
    }//GEN-LAST:event_jButtonGlobalDataActionPerformed

    private void populateTable() {
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (MonitoredEnvironment env : monitoredEnvironments) {
            tableModel.addRow(new String[]{env.getUsername(), env.getIndividualCount() + ""});
        }
        jTableEnvironments.setModel(tableModel);
    }

    private void removeEnvironmentByName(String name) {
        MonitoredEnvironment remover = getEnvironmentByName(name);
        if (remover != null) {
            monitoredEnvironments.remove(remover);
        }
    }
    
    private String getUserFromTableRow(int row) {
        return (String) jTableEnvironments.getModel().getValueAt(row, 0);
    }

    private MonitoredEnvironment getEnvironmentByName(String name) {
        for (int i = 0; i < monitoredEnvironments.size(); i++) {
            if (monitoredEnvironments.get(i).getUsername().equals(name)) {
                return monitoredEnvironments.get(i);
            }
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new WorldManagement().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddEnvironment;
    private javax.swing.JButton jButtonDetail;
    private javax.swing.JButton jButtonGlobalData;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JLabel jLabelEnvironments;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEnvironments;
    // End of variables declaration//GEN-END:variables

    /**
     * requires user input throught a JOptionPane
     * @return
     */
    private String inputConnectionUser() {
        return JOptionPane.showInputDialog(this, "Insira o identificador do ecossistema", "", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Updates the information of an Environment
     * @param user
     * @param unserializeEnvironment
     */
    public void updateEnvironmentInfo(String user, EnvironmentSerializable unserializeEnvironment) {
        removeEnvironmentByName(user);
        monitoredEnvironments.add(serializableToMonitoredEnvironment(user, unserializeEnvironment));
        populateTable();
    }

    private MonitoredEnvironment serializableToMonitoredEnvironment(String user, EnvironmentSerializable serializable) {
        MonitoredEnvironment monitored = new MonitoredEnvironment(user);
        monitored.setFoodCount(serializable.getFoodCount());
        monitored.setIndividualCount(serializable.getIndividualCount());
        monitored.setTimeSpeed(serializable.getTimeSpeed());
        monitored.setPlaying(true);
        return monitored;
    }
}
