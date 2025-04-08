package org.exolin.citysim.ui.budget;

import java.math.BigDecimal;

/**
 *
 * @author Thomas
 */
public class BudgetLinePanel extends javax.swing.JPanel
{
    private BigDecimal taxRevenue = BigDecimal.ZERO;
    private BigDecimal expenses = BigDecimal.ZERO;

    /**
     * Creates new form BudgetLinePanel
     * @param title
     */
    public BudgetLinePanel(String title)
    {
        initComponents();
        titleLabel.setText(title);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        incomeLabel = new javax.swing.JLabel();
        expenseLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 300));
        jPanel1.setLayout(new java.awt.GridLayout());

        incomeLabel.setForeground(new java.awt.Color(0, 204, 0));
        incomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        incomeLabel.setText("income");
        jPanel1.add(incomeLabel);

        expenseLabel.setForeground(new java.awt.Color(255, 51, 51));
        expenseLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        expenseLabel.setText("expense");
        jPanel1.add(expenseLabel);

        add(jPanel1, java.awt.BorderLayout.EAST);

        titleLabel.setText("Title");
        add(titleLabel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel expenseLabel;
    private javax.swing.JLabel incomeLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    void resetValues()
    {
        taxRevenue = BigDecimal.ZERO;
        expenses = BigDecimal.ZERO;
    }

    void updateValues(BigDecimal taxRevenue, BigDecimal maintenance)
    {
        this.taxRevenue = this.taxRevenue.add(taxRevenue);
        this.expenses = this.expenses.add(maintenance);
    }

    void showValues()
    {
        incomeLabel.setText(getText(taxRevenue));
        expenseLabel.setText(getText(expenses));
    }

    private String getText(BigDecimal expenses)
    {
        if(expenses.equals(BigDecimal.ZERO))
            return "";
        
        return expenses.toPlainString();
    }
}
