/*
 * This file is part of DALS - Distributed Artificial Life Simulation.
 * DALS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DALS.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * EnvironmentDetail.java
 *
 * Created on 28/11/2010, 12:54:27
 */

package genetic.view;

/**
 *
 * @author romulo
 */
public class EnvironmentDetail extends javax.swing.JFrame {

    /** Creates new form EnvironmentDetail
     * @param individualCount
     * @param foodCount
     */
    public EnvironmentDetail(int individualCount, int foodCount) {
        initComponents();
        jLabelIndividualValue.setText(individualCount + "");
        jLabelFoodValue.setText(foodCount + "");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelIndividuals = new javax.swing.JLabel();
        jLabelFoods = new javax.swing.JLabel();
        jLabelIndividualValue = new javax.swing.JLabel();
        jLabelFoodValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelIndividuals.setText("Population:");

        jLabelFoods.setText("Food:");

        jLabelIndividualValue.setText("-");

        jLabelFoodValue.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelIndividuals)
                        .addGap(12, 12, 12)
                        .addComponent(jLabelIndividualValue))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelFoods)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelFoodValue)))
                .addContainerGap(291, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelIndividuals)
                    .addComponent(jLabelIndividualValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFoods)
                    .addComponent(jLabelFoodValue))
                .addContainerGap(240, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelFoodValue;
    private javax.swing.JLabel jLabelFoods;
    private javax.swing.JLabel jLabelIndividualValue;
    private javax.swing.JLabel jLabelIndividuals;
    // End of variables declaration//GEN-END:variables

}
