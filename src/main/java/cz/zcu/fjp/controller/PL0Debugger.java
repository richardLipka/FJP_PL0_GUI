package cz.zcu.fjp.controller;

import cz.zcu.fjp.model.Instruction;
import cz.zcu.fjp.model.Stack;
import cz.zcu.fjp.model.StackItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * Singleton PL0Debugger takes care about main control of PL0 instructions
 * Sets instruction and stack information
 */
public class PL0Debugger {

	private static PL0Debugger instance = null;
	
	private Stack stack = new Stack();
	private ObservableList<StackItem> stackItemsToAdd;// = FXCollections.observableArrayList();
	
	protected PL0Debugger() {

	}
	
	public static PL0Debugger getInstance() {
		if(instance == null) {
	         instance = new PL0Debugger();
	      }
	      return instance;
	}
	
	public Stack getFutureStack() {		
		return stack;
	}	

	public Instruction getFutureInstruction(Instruction actual, ObservableList<Instruction> instructions) {
		
		ObservableList<TreeItem<StackItem>> stackItems = getActualList();
		
		Instruction future = null;
		if (actual == null) { //first instruction
			future = instructions.get(0);
		} else {
			
			System.out.println("Actual instruction: " + actual.toString());
			switch (actual.getInstruction()) {
				case "LIT":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					
					stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, actual.getOperand())));
					stack.setTop(getLast(stackItems));
					trySetPC(future);
					break;
				}
				case "OPR":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					
					switch (actual.getOperand()) {
						case 0:{ //return
							//TODO implement level
							break;
						}
						case 1:{ //negate
							StackItem item = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item.getValue() * (-1);
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 2:{ //sum
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item1.getValue() + item2.getValue();
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 3:{ //substract
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item1.getValue() - item2.getValue();
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 4:{ //multiply
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item1.getValue() * item2.getValue();
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 5:{ //divide
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item1.getValue() / item2.getValue();
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 6:{ //modulo
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item1.getValue() % item2.getValue();
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 7:{//odd?
							StackItem item = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = item.getValue() % 2;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 8:{ //equal
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = (item1.getValue() == item2.getValue()) ? 1 : 0;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 9:{ //not equal
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = (item1.getValue() != item2.getValue()) ? 1 : 0;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 10:{ //less than
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = (item1.getValue() < item2.getValue()) ? 1 : 0;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 11:{ //greater than or equal
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = (item1.getValue() >= item2.getValue()) ? 1 : 0;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 12:{ //greater than
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = (item1.getValue() > item2.getValue()) ? 1 : 0;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
						case 13:{ //less than or equal
							StackItem item2 = stackItems.remove(stackItems.size() - 1).getValue();
							StackItem item1 = stackItems.remove(stackItems.size() - 1).getValue();
							int returnValue = (item1.getValue() <= item2.getValue()) ? 1 : 0;
							stackItems.add(new TreeItem<StackItem>(new StackItem(getLast(stackItems).getIndex() + 1, returnValue)));
							break;
						}
					}
					stack.setTop(getLast(stackItems));
					trySetPC(future);
					break;
				}
				case "LOD":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					
					StackItem item = new StackItem(getLast(stackItems).getIndex() + 1, stackItems.get(getBase(actual.getLevel()) + actual.getOperand()).getValue().getValue());
					stackItems.add(new TreeItem<StackItem>(item));
					stack.setTop(getLast(stackItems));
					trySetPC(future);
					break;
				}
				case "STO":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					
					StackItem item = stackItems.remove(stackItems.size() - 1).getValue();
					item.setIndex(actual.getOperand() + 2 + getBase(actual.getLevel()));
					stackItems.set(getBase(actual.getLevel()) + actual.getOperand(), new TreeItem<StackItem>(item));
					stack.setTop(getLast(stackItems));
					trySetPC(future);
					break;
				}
				case "CAL":{
					future = tryGetInstruction(actual.getOperand(), instructions);
					
					StackItem item1 = new StackItem(stack.getTop().getIndex() + 1, getBase(actual.getLevel()));
					StackItem item2 = new StackItem(stack.getTop().getIndex() + 2, stack.getBase().getIndex());
					StackItem item3 = new StackItem(stack.getTop().getIndex() + 3, stack.getProgramCounter() + 1);
					
					//stack.setBase(stack.getTop());
					stack.setProgramCounter(actual.getOperand());
					
					stackItemsToAdd = FXCollections.observableArrayList(item1, item2, item3);
					break;
				}
				case "INT":{
					future = tryGetInstruction(actual.getIndex() + 1, instructions);
					
					if (stackItemsToAdd != null && !stackItemsToAdd.isEmpty()) {
						if (actual.getOperand() > stackItemsToAdd.size()) {
							for (int i = stackItemsToAdd.size(); i < actual.getOperand(); i++) {
								int plus = 1;
								int index = stackItemsToAdd.get(stackItemsToAdd.size() - 1).getIndex() + plus;
								stackItemsToAdd.add(new StackItem(index, 0));
								plus++;
							}
						}
						
						stack.setBase(stackItemsToAdd.get(0));
						
						TreeItem<StackItem> subroot = new TreeItem<StackItem>(stackItemsToAdd.get(0));
						subroot.setExpanded(true);
						for (int i = 1; i < stackItemsToAdd.size(); i++) {
							subroot.getChildren().add(new TreeItem<StackItem>(stackItemsToAdd.get(i)));
						}
						stackItems.add(subroot);
						stackItemsToAdd.removeAll(stackItemsToAdd);
						
						stack.setLevel(stack.getLevel() + 1);
						stack.setTop(getLast(stackItems));
					} else {
						int size = stackItems.size() + 1;
						stack.setBase(new StackItem(size, 0));
						
						TreeItem<StackItem> subroot = new TreeItem<StackItem>(new StackItem(size, 0));
						subroot.setExpanded(true);
						
						for (int i = size + 1; i < size + actual.getOperand(); i++) {
							subroot.getChildren().add(new TreeItem<StackItem>(new StackItem(i, 0)));
						}	
						stackItems.add(subroot);
						
						stack.setLevel(stack.getLevel() + 1);
						stack.setTop(getLast(stackItems));
					}
					
					
					trySetPC(future);
					break;
				}
				case "JMP":{
					future = tryGetInstruction(actual.getOperand(), instructions);	
					
					trySetPC(future);
					break;	
				}
				case "JMC":{
					
					if (stack.getTop().getValue() == 0) {
						future = tryGetInstruction(actual.getOperand(), instructions);
					} else {
						future = tryGetInstruction(actual.getIndex() + 1, instructions);
					}
					
					stackItems.remove(stackItems.size() - 1);
					stack.setTop(getLast(stackItems));
					trySetPC(future);				
					break;
				}
				case "RET":{
					
					
					if (stack.getBase().getIndex() <= 1) {
						future = null;
						stack.setProgramCounter(-1);
					} else {
						stack.setTop(stackItems.get(stack.getBase().getIndex() - 2).getValue());
						future = tryGetInstruction(stackItems.get(stack.getTop().getIndex() + 2).getValue().getValue(), instructions);
								
						stack.setProgramCounter(future.getIndex());
						stack.setBase(stackItems.get(stackItems.get(stack.getTop().getIndex() + 1).getValue().getValue() - 1).getValue());
						
						stackItems.subList(stack.getTop().getIndex(), stackItems.size()).clear();
					}	
					
					break;
				}		
			}
		}
//		stack.setStackItems(stackItems);
		
		if (future == null) {
			stack.setProgramCounter(-1);	
		}
		
//		TreeItem<StackItem> root = new TreeItem<StackItem>(new StackItem(-1, -1));
//		root.setExpanded(true);
//		stack.getStackItems().stream().forEach((stackItem) -> {
//            root.getChildren().add(new TreeItem<>(stackItem));
//        });   
//		root.getChildren().addAll(stackItems);
//		stack.setRoot(root);
		
		//setRoot(stackItems);
		return future;
		
	}

	private void setRoot(ObservableList<TreeItem<StackItem>> stackItems) {
		TreeItem<StackItem> root = stack.getRoot();
		root.setExpanded(true);
		stack.setRoot(root);
	}

	private ObservableList<TreeItem<StackItem>> getActualList() {
		int level = stack.getLevel();
		ObservableList<TreeItem<StackItem>> list;
		if (level > 0) {
			list = stack.getRoot().getChildren();
			for (int i = 0; i < level; i++) {
				list = list.get(list.size() - 1).getChildren();
				System.out.println("level " + list.get(0));
			}
		} else {
			list = stack.getRoot().getChildren();
			System.out.println("only root ");
		}
		return list;
	}

	private void trySetPC(Instruction future) {
		if (future == null) {
			stack.setProgramCounter(-1);	
		} else {
			stack.setProgramCounter(future.getIndex());
		}	
	}

	public void nullStacks() {
		stack = new Stack();
	}
	
	public Instruction tryGetInstruction(int index, ObservableList<Instruction> instructions) {
		Instruction instruction = null;
		try {
			instruction = instructions.get(index);
			System.out.println("Future instruction: " + instruction.toString());
		} catch (Exception e) {
			// do nothing, return null
		}
		return instruction;
	}
	
	public int getBase(int level){
		TreeItem<StackItem> newBase = stack.getRoot();
		System.out.println(newBase);
		while ( level > 1 ){
			newBase = newBase.getParent();
			level--;
			//if (newBase.getValue().getIndex() == -1) newBase = newBase.getChildren().get(0);
			System.out.println(newBase);
		}
		return newBase.getValue().getIndex();
	}
	
	private StackItem getLast(ObservableList<TreeItem<StackItem>> stackItems) {
		return stackItems.get(stackItems.size() - 1).getValue();
	}
}
