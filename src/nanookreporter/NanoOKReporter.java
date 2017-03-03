/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nanookreporter;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JTable;

/**
 *
 * @author leggettr
 */
public class NanoOKReporter extends javax.swing.JFrame {
    private NanoOKSample sample;
    private AROMap aroMap;
    private int pf;
    private int type;
    private int amrChunkSliderMax = 0;
    private int taxonChunkSliderMax = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy HH:mm:ss");
    private int amrColumnWidths[] = {0, 0, 0, 0, 0};
    private int taxonColumnWidths[] = {0, 0, 0, 0};
    
    /**
     * Creates new form ReporterFrame
     */
    public NanoOKReporter() {
        super("NanoOKReporter");
        initComponents();
        setTaxonColumnWidths();
        setAmrColumnWidths();
        AROMap.readMapFile("/Users/leggettr/Documents/Databases/CARD_1.1.1_Download_17Oct16/aro.csv");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        PassFailButtonGroup = new javax.swing.ButtonGroup();
        Template2DButtonGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        StatsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        timeRunningLabel = new javax.swing.JLabel();
        readsAnalysedLabel = new javax.swing.JLabel();
        chunksLabel = new javax.swing.JLabel();
        TaxonPanel = new javax.swing.JPanel();
        taxonScrollPane = new javax.swing.JScrollPane();
        taxonTable = new javax.swing.JTable();
        taxonChunkSlider = new javax.swing.JSlider();
        taxonChunkLabel = new javax.swing.JLabel();
        taxonChunkTimeLabel = new javax.swing.JLabel();
        CardPanel = new javax.swing.JPanel();
        amrScrollPane = new javax.swing.JScrollPane();
        amrTable = new javax.swing.JTable();
        amrChunkSlider = new javax.swing.JSlider();
        amrChunkLabel = new javax.swing.JLabel();
        amrChunkTimeLabel = new javax.swing.JLabel();
        sampleTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        ChooseButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        failRadioButton = new javax.swing.JRadioButton();
        passRadioButton = new javax.swing.JRadioButton();
        twoDRadioButton = new javax.swing.JRadioButton();
        templateRadioButton = new javax.swing.JRadioButton();
        progressLabel = new javax.swing.JLabel();
        rescanButton = new javax.swing.JButton();

        jTextField5.setText("jTextField5");

        jLabel5.setText("Chunk:");

        jTextField6.setEditable(false);
        jTextField6.setText("jTextField5");

        jTextField7.setEditable(false);
        jTextField7.setText("jTextField7");

        jLabel7.setText("of");

        jLabel9.setText("jLabel9");

        jLabel8.setText("jLabel8");

        jTextField2.setEditable(false);
        jTextField2.setText("jTextField2");

        jTextField3.setEditable(false);
        jTextField3.setText("jTextField3");

        jTextField4.setEditable(false);
        jTextField4.setText("jTextField4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Time running:");

        jLabel3.setText("Reads analysed:");

        jLabel4.setText("Chunks:");

        javax.swing.GroupLayout StatsPanelLayout = new javax.swing.GroupLayout(StatsPanel);
        StatsPanel.setLayout(StatsPanelLayout);
        StatsPanelLayout.setHorizontalGroup(
            StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatsPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chunksLabel)
                    .addComponent(readsAnalysedLabel)
                    .addComponent(timeRunningLabel))
                .addContainerGap(793, Short.MAX_VALUE))
        );
        StatsPanelLayout.setVerticalGroup(
            StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(timeRunningLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(readsAnalysedLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StatsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(chunksLabel))
                .addContainerGap(530, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stats", StatsPanel);

        taxonScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        taxonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Rank", "Count", "Id", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        taxonTable.setMaximumSize(null);
        taxonTable.setShowGrid(false);
        taxonScrollPane.setViewportView(taxonTable);

        taxonChunkSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                taxonChunkSliderStateChanged(evt);
            }
        });

        taxonChunkLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        taxonChunkLabel.setText("Chunk: 5000/5000");

        taxonChunkTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        taxonChunkTimeLabel.setText("22/12/2017 13:34:05");

        javax.swing.GroupLayout TaxonPanelLayout = new javax.swing.GroupLayout(TaxonPanel);
        TaxonPanel.setLayout(TaxonPanelLayout);
        TaxonPanelLayout.setHorizontalGroup(
            TaxonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(taxonScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
            .addGroup(TaxonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taxonChunkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taxonChunkSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taxonChunkTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        TaxonPanelLayout.setVerticalGroup(
            TaxonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TaxonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TaxonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(taxonChunkSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taxonChunkTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(taxonChunkLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taxonScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Taxon", TaxonPanel);

        amrScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        amrTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rank", "Count", "ID", "ARO", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        amrScrollPane.setViewportView(amrTable);
        if (amrTable.getColumnModel().getColumnCount() > 0) {
            amrTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            amrTable.getColumnModel().getColumn(1).setPreferredWidth(10);
            amrTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        }

        amrChunkSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                amrChunkSliderStateChanged(evt);
            }
        });

        amrChunkLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        amrChunkLabel.setText("Chunk: 5000/5000");
        amrChunkLabel.setMaximumSize(new java.awt.Dimension(120, 16));
        amrChunkLabel.setMinimumSize(new java.awt.Dimension(120, 16));

        amrChunkTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        amrChunkTimeLabel.setText("22/12/2017 13:34:05");

        javax.swing.GroupLayout CardPanelLayout = new javax.swing.GroupLayout(CardPanel);
        CardPanel.setLayout(CardPanelLayout);
        CardPanelLayout.setHorizontalGroup(
            CardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(amrScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
            .addGroup(CardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(amrChunkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amrChunkSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amrChunkTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        CardPanelLayout.setVerticalGroup(
            CardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(amrChunkTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(amrChunkLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(amrChunkSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amrScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("AMR", CardPanel);

        sampleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sampleTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Sample directory:");

        ChooseButton.setText("Choose...");
        ChooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseButtonActionPerformed(evt);
            }
        });

        PassFailButtonGroup.add(failRadioButton);
        failRadioButton.setText("Fail");
        failRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                failRadioButtonActionPerformed(evt);
            }
        });

        PassFailButtonGroup.add(passRadioButton);
        passRadioButton.setSelected(true);
        passRadioButton.setText("Pass");
        passRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passRadioButtonActionPerformed(evt);
            }
        });

        Template2DButtonGroup.add(twoDRadioButton);
        twoDRadioButton.setSelected(true);
        twoDRadioButton.setText("2D");
        twoDRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoDRadioButtonActionPerformed(evt);
            }
        });

        Template2DButtonGroup.add(templateRadioButton);
        templateRadioButton.setText("Template");
        templateRadioButton.setToolTipText("");
        templateRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                templateRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(templateRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(twoDRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(passRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(failRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(twoDRadioButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(failRadioButton)
                        .addComponent(passRadioButton)
                        .addComponent(templateRadioButton)))
                .addGap(19, 19, 19))
        );

        progressLabel.setText("Ready");

        rescanButton.setText("Rescan");
        rescanButton.setEnabled(false);
        rescanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescanButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sampleTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ChooseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rescanButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sampleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChooseButton)
                    .addComponent(rescanButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Stats");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseButtonActionPerformed
        int result;

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Pick sample directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            sampleTextField.setText(chooser.getSelectedFile().toString());
            sample = new NanoOKSample(chooser.getSelectedFile().toString());
            ChunkLoader loader = new ChunkLoader(this, sample);
            loader.execute();
        } else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_ChooseButtonActionPerformed

    private void passRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passRadioButtonActionPerformed
        if (sample != null) {
            updateStats();
            updateTableAmr();
            updateTableTaxon();
        }
    }//GEN-LAST:event_passRadioButtonActionPerformed

    private void failRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_failRadioButtonActionPerformed
        if (sample != null) {
            updateStats();
            updateTableAmr();
            updateTableTaxon();
        }
    }//GEN-LAST:event_failRadioButtonActionPerformed

    private void templateRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_templateRadioButtonActionPerformed
        if (sample != null) {
            updateStats();
            updateTableAmr();
            updateTableTaxon();
        }
    }//GEN-LAST:event_templateRadioButtonActionPerformed

    private void sampleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sampleTextFieldActionPerformed

    private void twoDRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoDRadioButtonActionPerformed
        if (sample != null) {
            updateStats();
            updateTableAmr();
            updateTableTaxon();
        }
    }//GEN-LAST:event_twoDRadioButtonActionPerformed

    private void amrChunkSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_amrChunkSliderStateChanged
        if (sample != null) {
            BlastChunkSet amrChunkSet = sample.getCardFile().getChunkSet(type, pf);
            amrChunkLabel.setText("Chunk "+ amrChunkSlider.getValue() + "/" + amrChunkSet.getNumberOfChunks());
            amrChunkTimeLabel.setText(dateFormat.format(amrChunkSet.getChunkLastModified(amrChunkSlider.getValue())));
            if (amrChunkSlider.getValueIsAdjusting() == false) {
                amrChunkSet.setSelectedChunk(amrChunkSlider.getValue());
                updateTableAmr();
            }
        }
    }//GEN-LAST:event_amrChunkSliderStateChanged

    private void taxonChunkSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_taxonChunkSliderStateChanged
        if (sample != null) {
            BlastChunkSet taxonChunkSet = sample.getNtFile().getChunkSet(type, pf);
            taxonChunkLabel.setText("Chunk "+ taxonChunkSlider.getValue() + "/" + taxonChunkSet.getNumberOfChunks());
            taxonChunkTimeLabel.setText(dateFormat.format(taxonChunkSet.getChunkLastModified(taxonChunkSlider.getValue())));
            if (taxonChunkSlider.getValueIsAdjusting() == false) {
                taxonChunkSet.setSelectedChunk(taxonChunkSlider.getValue());
                updateTableTaxon();
            }
        }
    }//GEN-LAST:event_taxonChunkSliderStateChanged

    private void rescanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rescanButtonActionPerformed
        System.out.println("Rescanning!");
        ChunkLoader loader = new ChunkLoader(this, sample);
        loader.execute();
    }//GEN-LAST:event_rescanButtonActionPerformed

    public void updateStats() {
        timeRunningLabel.setText("0hr0m");
        readsAnalysedLabel.setText("0");
        chunksLabel.setText("0");
    }
    
    public void findType() {
        if (passRadioButton.isSelected()) {
            pf = BlastFile.TYPE_PASS;
        } else {
            pf = BlastFile.TYPE_FAIL;
        }
        
        if (templateRadioButton.isSelected()) {
            type = BlastFile.TYPE_TEMPLATE;
        } else {
            type = BlastFile.TYPE_2D;
        }
    }
    
    public void updateTableAmr() {                                
        setStatus("Updating AMR table");
        findType();

        BlastChunkSet amrChunkSet = sample.getCardFile().getChunkSet(type, pf);
        sample.getCardFile().countSet(type, pf);
        sample.getCardFile().updateTable(amrTable, type, pf);
        amrChunkLabel.setText("Chunk "+ amrChunkSet.getSelectedChunk() + "/" + amrChunkSet.getNumberOfChunks());
        if (amrChunkSliderMax != amrChunkSet.getNumberOfChunks()) {
            amrChunkSliderMax = amrChunkSet.getNumberOfChunks();
            amrChunkSlider.setMaximum(amrChunkSet.getNumberOfChunks());
            amrChunkSet.setSelectedChunk(amrChunkSliderMax);
        }
        amrChunkSlider.setValue(amrChunkSet.getSelectedChunk());
        amrTable.setModel(amrChunkSet);
        amrTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAmrColumnWidths();
        
        setStatus("Table updated");
    }
    
    public void updateTableTaxon() {        
        setStatus("Updating taxonomy table");
        findType();

        BlastChunkSet taxonChunkSet = sample.getNtFile().getChunkSet(type, pf);
        sample.getNtFile().countSet(type, pf);
        sample.getNtFile().updateTable(taxonTable, type, pf);
        taxonChunkLabel.setText("Chunk "+ taxonChunkSet.getSelectedChunk() + "/" + taxonChunkSet.getNumberOfChunks());
        if (taxonChunkSliderMax != taxonChunkSet.getNumberOfChunks()) {
            taxonChunkSliderMax = taxonChunkSet.getNumberOfChunks();
            taxonChunkSlider.setMaximum(taxonChunkSet.getNumberOfChunks());
            taxonChunkSet.setSelectedChunk(taxonChunkSliderMax);
        }
        taxonChunkSlider.setValue(taxonChunkSet.getSelectedChunk());
        taxonTable.setModel(taxonChunkSet);
        taxonTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setTaxonColumnWidths();

        setStatus("Table updated");
    }    
    
    public void setAmrColumnWidths() {
        amrTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        amrTable.getColumnModel().getColumn(1).setPreferredWidth(70);        
        amrTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        amrTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        amrTable.getColumnModel().getColumn(4).setPreferredWidth(600);
    }
    
    public void setTaxonColumnWidths() {
        taxonTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        taxonTable.getColumnModel().getColumn(1).setPreferredWidth(70);        
        taxonTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        taxonTable.getColumnModel().getColumn(3).setPreferredWidth(600);
    }
    
    public void setStatus(String s) {
        progressLabel.setText(s);
    }
    
    /**
     * Centres a window on the screen.
     *
     * @param  w the window to centre
     */
    public static void centreWindow(Window w) {
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         Rectangle frameSize = w.getBounds();

         w.setLocation((screenSize.width - frameSize.width) / 2,
                       (screenSize.height - frameSize.height) / 2);
    }    
    
    public void handleNewDirectory(String directory) {
    }
    
    public void loadFinished() {
        rescanButton.setEnabled(true);
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NanoOKReporter rf = new NanoOKReporter();
                centreWindow(rf);
                rf.setVisible(true);
                System.out.println("Running");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardPanel;
    private javax.swing.JButton ChooseButton;
    private javax.swing.ButtonGroup PassFailButtonGroup;
    private javax.swing.JPanel StatsPanel;
    private javax.swing.JPanel TaxonPanel;
    private javax.swing.ButtonGroup Template2DButtonGroup;
    private javax.swing.JLabel amrChunkLabel;
    private javax.swing.JSlider amrChunkSlider;
    private javax.swing.JLabel amrChunkTimeLabel;
    private javax.swing.JScrollPane amrScrollPane;
    private javax.swing.JTable amrTable;
    private javax.swing.JLabel chunksLabel;
    private javax.swing.JRadioButton failRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JRadioButton passRadioButton;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JLabel readsAnalysedLabel;
    private javax.swing.JButton rescanButton;
    private javax.swing.JTextField sampleTextField;
    private javax.swing.JLabel taxonChunkLabel;
    private javax.swing.JSlider taxonChunkSlider;
    private javax.swing.JLabel taxonChunkTimeLabel;
    private javax.swing.JScrollPane taxonScrollPane;
    private javax.swing.JTable taxonTable;
    private javax.swing.JRadioButton templateRadioButton;
    private javax.swing.JLabel timeRunningLabel;
    private javax.swing.JRadioButton twoDRadioButton;
    // End of variables declaration//GEN-END:variables
}
