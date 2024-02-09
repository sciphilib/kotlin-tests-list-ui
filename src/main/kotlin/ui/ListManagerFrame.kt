package ui

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.type.TypeFactory
import com.nstu.lab1.dataStructures.ListSerializer
import data.Fraction
import data.Point2D
import data.factories.FractionFactory
import data.factories.Point2DFactory
import dataStructures.MyList
import kotlinx.serialization.ExperimentalSerializationApi
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.io.IOException
import javax.swing.*


class ListManagerFrame<T : Any> : JFrame() {
    private var dataList: MyList<T>?
    private var listModel: DefaultListModel<Any>
    private val displayList: JList<Any>
    private val dataTypeComboBox: JComboBox<String>
    private var currentDataType: String? = null
    private val applyButton: JButton
    private val resetButton: JButton

    init {
        dataList = MyList()
        listModel = DefaultListModel()
        displayList = JList(listModel)
        val scrollPane = JScrollPane(displayList)

        dataTypeComboBox = JComboBox(arrayOf("Integer", "Point2D", "Fraction"))
        val addButton = JButton("Add")
        val addByIndexButton = JButton("AddByIndex")
        val removeButton = JButton("Remove")
        val removeByIndexButton = JButton("RemoveByIndex")
        val saveButton = JButton("Save to JSON")
        val loadButton = JButton("Load from JSON")
        val sortButton = JButton("Sort")
        applyButton = JButton("Apply")
        resetButton = JButton("Reset")

        applyButton.addActionListener { e: ActionEvent -> this.applyDataType(e) }
        resetButton.addActionListener { e: ActionEvent -> this.resetList(e) }
        addButton.addActionListener { e: ActionEvent -> this.addItem(e) }
        addByIndexButton.addActionListener { e: ActionEvent -> this.addItemByIndex(e) }
        removeButton.addActionListener { e: ActionEvent -> this.removeItem(e) }
        removeByIndexButton.addActionListener { e: ActionEvent -> this.removeItemByIndex(e) }
         saveButton.addActionListener { e: ActionEvent -> this.saveToJson(e) }
       loadButton.addActionListener { e: ActionEvent -> this.loadFromJson(e) }
        sortButton.addActionListener { e: ActionEvent -> this.sortList(e) }

        val controlPanel = JPanel()
        controlPanel.add(dataTypeComboBox)
        controlPanel.add(applyButton)
        controlPanel.add(resetButton)
        controlPanel.add(addButton)
        controlPanel.add(addByIndexButton)
        controlPanel.add(removeButton)
        controlPanel.add(removeByIndexButton)
        controlPanel.add(sortButton)
        controlPanel.add(saveButton)
        controlPanel.add(loadButton)

        this.add(scrollPane, BorderLayout.CENTER)
        this.add(controlPanel, BorderLayout.SOUTH)
        this.setSize(400, 300)
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.title = "List Manager"
        this.isVisible = true
    }

    private fun addItem(e: ActionEvent) {
        val dataType = dataTypeComboBox.selectedItem as String
        var newItem: T? = null

        when (dataType) {
            "Integer" -> {
                val numberStr = JOptionPane.showInputDialog("Enter a number:")
                newItem = numberStr.toInt() as T
            }

            "Point2D" -> newItem = point2DFromInput as T
            "Fraction" -> newItem = fractionFromInput as T
        }
        if (newItem != null) {
            dataList?.add(newItem)
            listModel.addElement(newItem)
        }
    }

    private fun addItemByIndex(e: ActionEvent) {
        val dataType = dataTypeComboBox.selectedItem as String
        var newItem: T? = null
        val indexStr = JOptionPane.showInputDialog("Enter an index:") as String
        val index = indexStr.toInt()

        if (index < 0 || index > dataList?.size()!!) {
            JOptionPane.showMessageDialog(this, "Incorrect index")
            return
        }

        when (dataType) {
            "Integer" -> {
                val numberStr = JOptionPane.showInputDialog("Enter a number:")
                newItem = numberStr.toInt() as T
            }

            "Point2D" -> newItem = point2DFromInput as T
            "Fraction" -> newItem = fractionFromInput as T
        }
        if (newItem != null) {
            dataList!!.add(newItem, index)
            listModel.insertElementAt(newItem, index)
        }
    }

    private fun applyDataType(e: ActionEvent) {
        currentDataType = dataTypeComboBox.selectedItem as String
        dataList = MyList()
        listModel.clear()
        dataTypeComboBox.isEnabled = false
        resetButton.isEnabled = true
    }

    private fun resetList(e: ActionEvent) {
        currentDataType = null
        dataList?.clear()
        listModel.clear()
        dataTypeComboBox.isEnabled = true
        resetButton.isEnabled = false
    }

    private fun removeItem(e: ActionEvent) {
        dataList!!.remove(dataList!!.size()-1)
        listModel.remove(listModel.size() - 1)
    }
    private fun removeItemByIndex(e: ActionEvent) {
        val indexStr = JOptionPane.showInputDialog("Enter an index:") as String
        val index = indexStr.toInt()
        if (index < 0 || index >= dataList?.size()!!) {
            JOptionPane.showMessageDialog(this, "Incorrect index")
            return
        }
        dataList!!.remove(index)
        listModel.remove(index)
    }
   @OptIn(ExperimentalSerializationApi::class)
   private fun saveToJson(e: ActionEvent) {
       val fileChooser = JFileChooser()
       if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
           val file = fileChooser.selectedFile
           try {
               val mapper = ObjectMapper()
               val module = SimpleModule()
               module.addSerializer(MyList::class.java, ListSerializer<Any?>())
               mapper.registerModule(module)
               mapper.writeValue(file, dataList)
           } catch (ioException: IOException) {
               JOptionPane.showMessageDialog(
                   this, "Error saving to JSON: " + ioException.message,
                   "Save Error", JOptionPane.ERROR_MESSAGE
               )
           }
       }
    }

    fun loadFromJson(e: ActionEvent) {
        val fileChooser = JFileChooser()
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            val file = fileChooser.selectedFile
            try {
                val mapper = ObjectMapper()
                val loadedList: MyList<*> = when(currentDataType) {
                    "Point2D" -> mapper.readValue(file, object : TypeReference<MyList<Point2D>>() {})
                    "Fraction" -> mapper.readValue(file, object : TypeReference<MyList<Fraction>>() {})
                    "Integer" -> mapper.readValue(file, object : TypeReference<MyList<Int>>() {})
                    else -> throw IllegalArgumentException("Unknown data type")
                }
                updateList(loadedList)
            } catch (ioException: IOException) {
                JOptionPane.showMessageDialog(this, "Error loading from JSON: " + ioException.message,
                    "Load Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }
    private fun sortList(e: ActionEvent) {
        val dataType = dataTypeComboBox.selectedItem as String
        val point2dFactory: Point2DFactory = Point2DFactory()
        val fractionFactory: FractionFactory = FractionFactory()

        when (dataType) {
            "Point2D" -> dataList?.sort(point2dFactory.getComparator() as Comparator<T>)
            "Fraction" -> dataList?.sort(fractionFactory.getComparator() as Comparator<T>)
            "Integer"-> dataList!!.sort(Comparator.naturalOrder<Int>() as Comparator<T>)
            else -> throw IllegalArgumentException("Unsupported type: $dataType")
        }
        updateListModel()
    }

    private fun updateList(newList: MyList<*>?) {
        listModel.clear()
        listModel=newList!!.addAll(listModel)
      

    }

    private fun updateListModel() {
        listModel.clear()
        if (dataList != null) {
            listModel= dataList!!.addAll(listModel)
        }
    }

    private val point2DFromInput: Point2D?
        get() {
            val xField = JTextField(5)
            val yField = JTextField(5)

            val myPanel = JPanel()
            myPanel.add(JLabel("x:"))
            myPanel.add(xField)
            myPanel.add(Box.createHorizontalStrut(15))
            myPanel.add(JLabel("y:"))
            myPanel.add(yField)

            val result = JOptionPane.showConfirmDialog(
                null, myPanel,
                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val x = xField.text.toInt()
                val y = yField.text.toInt()
                return Point2D(x, y)
            }
            return null
        }
    //
    private val fractionFromInput: Fraction?
        get() {
            val wholePartField = JTextField(5)
            val numeratorField = JTextField(5)
            val denominatorField = JTextField(5)

            val myPanel = JPanel()
            myPanel.add(JLabel("Whole Part:"))
            myPanel.add(wholePartField)
            myPanel.add(Box.createHorizontalStrut(15))
            myPanel.add(JLabel("Numerator:"))
            myPanel.add(numeratorField)
            myPanel.add(JLabel("Denominator:"))
            myPanel.add(denominatorField)

            val result = JOptionPane.showConfirmDialog(
                null, myPanel,
                "Please Enter Whole Part, Numerator, and Denominator", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val wholePart = wholePartField.text.toInt()
                val numerator = numeratorField.text.toInt()
                val denominator = denominatorField.text.toInt()
                return Fraction(wholePart, numerator, denominator)
            }
            return null
        }
}