package com.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*CHANGE ACTIVITY:
1. 03/23/15 mtesense 
1.1 Initial Version
2. 05/18/16 Sun Zhang Wen @1@
2.1 Add new function updateSpecifySheetArrayByTCName()
2.2 Add new constant NECESSARY_COLUMN_NUM used in new function
*/
public class ExcelUtil {

	private static int NECESSARY_COLUMN_NUM = 6; /* @1@ */

	public static Map<String, String> getSpecifySheet(String excelpath, String sheetname, String caseName) {

		List<String> header = null;
		Map<String, String> rowmap = new HashMap<String, String>();

		boolean findrow = false;
		int rownumber = 0;

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			// the row index begin with 0
			int rows = sheet.getRows();
			// the column index begin with 0
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0).getContents().trim();
				header.add(columnindex, headerelement);
			}
			System.out.println("Current excel header is :" + header);
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				String cellcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();
				System.out.println("found the first column content in excel is:" + cellcontent);

				if (cellcontent.equalsIgnoreCase(caseName)) {
					System.out.println("Found the correct cell data,the content we found in excel is:" + cellcontent);
					findrow = true;
					rownumber = rowindex;
					break;
				} else {
					findrow = false;
				}
			}

			if (findrow) {
				for (int columnindex = 0; columnindex < columns; columnindex++) {
					String findcontent = sheet.getCell(columnindex, rownumber).getContents().trim();
					String mapheader = header.get(columnindex);
					rowmap.put(mapheader, findcontent);
				}
			}
			System.out.println("current Row data is :" + rowmap);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowmap;
	}

	public static List<Map<String, String>> getSpecifySheet(String excelpath, String sheetname) {

		List<String> header = null;
		Map<String, String> rowmap = new HashMap<String, String>();
		List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			// the row index begin with 0
			int rows = sheet.getRows();
			// the column index begin with 0
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0).getContents().trim();
				header.add(columnindex, headerelement);
			}
			System.out.println("Current excel header is :" + header);
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				for (int columnindex = 0; columnindex < columns; columnindex++) {
					String findcontent = sheet.getCell(columnindex, rowindex).getContents().trim();
					String mapheader = header.get(columnindex);
					rowmap.put(mapheader, findcontent);
					System.out.println("current Row data is :" + rowmap);
				}
				rowList.add(rowmap);
			}
			System.out.println("current RowList data is :" + rowList);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowList;
	}

	public static Map<String, ArrayList<String>> getSpecifySheetArray(String excelpath, String sheetname) {

		List<String> header = null;
		Map<String, ArrayList<String>> rowmap = new HashMap<String, ArrayList<String>>();

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			// the row index begin with 0
			int rows = sheet.getRows();
			String[] content = new String[rows];
			// the column index begin with 0
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerelement = sheet.getCell(columnindex, 0).getContents().trim();
				header.add(columnindex, headerelement);
			}
			System.out.println("Current excel header is :" + header);

			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String mapheader = header.get(columnindex);
				for (int rowindex = 1; rowindex < rows; rowindex++) {
					String findcontent = sheet.getCell(columnindex, rowindex).getContents().trim();

					content[rowindex] = findcontent;
					// System.out.println("current RowList data is :" +
					// colList);
				}
				if (!rowmap.containsKey(mapheader)) {
					rowmap.put(mapheader, new ArrayList<String>());
				}
				for (int i = 1; i < content.length; i++) {
					rowmap.get(mapheader).add(content[i]);
				}
				// System.out.println("current Row data is :" + rowmap);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowmap;
	}

	public static ArrayList<ArrayList<String>> getSpecifySheetArrayList(String excelpath, String sheetname) {
		ArrayList<ArrayList<String>> rowmap = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowArray = new ArrayList<String>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			// the row index begin with 0
			int rows = sheet.getRows();
			String[] content = new String[rows];
			// the column index begin with 0
			int columns = sheet.getColumns();

			for (int columnindex = 0; columnindex < columns; columnindex++) {
				rowArray = new ArrayList<String>();
				for (int rowindex = 1; rowindex < rows; rowindex++) {
					String findcontent = sheet.getCell(columnindex, rowindex).getContents().trim();
					content[rowindex] = findcontent;
				}
				for (int i = 1; i < content.length; i++) {
					rowArray.add(content[i]);
				}
				rowmap.add(rowArray);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowmap;
	}

	public static Map<String, String> getSpecifySheetByTCName(String excelpath, String sheetname, String caseName) {

		List<String> header = null;
		Map<String, String> rowmap = new HashMap<String, String>();

		boolean findrow = false;
		int rownumber = 0;
		header = new ArrayList<String>();

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			// the row index begin with 0
			int rows = sheet.getRows();
			// the column index begin with 0
			int columns = sheet.getColumns();

			for (int rowindex = 1; rowindex < rows; rowindex++) {
				String cellcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();
				if (cellcontent.equalsIgnoreCase(caseName)) {
					findrow = true;
					rownumber = rowindex;
					break;
				} else {
					findrow = false;
				}
			}

			if (findrow) {
				for (int columnindex = 0; columnindex < columns; columnindex++) {
					String headerelement = sheet.getCell(columnindex, rownumber - 1).getContents().trim();
					if (!headerelement.equals("")) {
						header.add(columnindex, headerelement);
						String findcontent = sheet.getCell(columnindex, rownumber).getContents().trim();
						String mapheader = header.get(columnindex);
						rowmap.put(mapheader, findcontent);
					} else {
						break;
					}
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowmap;
	}

	public static Map<String, ArrayList<String[]>> getSpecifySheetArrayByTCName(String excelpath, String sheetname,
			String caseName) {

		// List<String> header = null;
		Map<String, ArrayList<String[]>> rowmap = new HashMap<String, ArrayList<String[]>>();
		boolean findrow = false;
		int beginrownumber = 0;
		int endrownumber = 0;

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);
			// the row index begin with 0
			int rows = sheet.getRows();

			// the column index begin with 0
			int columns = sheet.getColumns();
			// header = new ArrayList<String>();
			System.out.println("Rows are :" + rows);
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				String cellcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();
				if (cellcontent.equalsIgnoreCase(caseName) && rowindex != rows - 1) {
					if (!findrow) {
						beginrownumber = rowindex;
						findrow = true;
					}
				} else if (cellcontent.equalsIgnoreCase(caseName) && rowindex == rows - 1) {
					if (beginrownumber == 0) {
						beginrownumber = rowindex;
						endrownumber = rowindex + 1;
						findrow = true;
					} else {
						endrownumber = rowindex;
					}
					break;
				} else {
					if (findrow) {
						endrownumber = rowindex;
						break;
					}
				}
			}
			System.out.println(endrownumber);
			System.out.println(beginrownumber);
			String[] a;
			if (findrow) {
				for (int columnindex = 0; columnindex < columns; columnindex++) {
					String mapheader = sheet.getCell(columnindex, beginrownumber - 1).getContents().trim();
					List<String[]> content = new ArrayList<String[]>();
					if (!mapheader.equals("")) {
						if (endrownumber == rows - 1) {
							for (int rowindex = beginrownumber; rowindex < (endrownumber + 1); rowindex++) {
								String findcontent = sheet.getCell(columnindex, rowindex).getContents().trim();
								a = new String[2];

								a[0] = findcontent;
								a[1] = Integer.toString(rowindex);
								content.add(a);
							}
						} else {
							for (int rowindex = beginrownumber; rowindex < (endrownumber); rowindex++) {
								String findcontent = sheet.getCell(columnindex, rowindex).getContents().trim();
								a = new String[2];

								a[0] = findcontent;
								a[1] = Integer.toString(rowindex);
								content.add(a);
							}
						}

						if (!rowmap.containsKey(mapheader)) {
							rowmap.put(mapheader, new ArrayList<String[]>());
						}
						for (int i = 0; i < content.size(); i++) {
							rowmap.get(mapheader).add(content.get(i));
						}
					} else {
						break;
					}
				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rowmap;
	}

	public static void appendCellValue(String excelpath, String sheetname, int row, int col, String content,
			Colour color) {

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			WritableWorkbook book = Workbook.createWorkbook(new File(excelpath), workbook);
			WritableSheet sheet = book.getSheet(sheetname);
			WritableCellFormat cellFormat1 = new WritableCellFormat();
			cellFormat1.setBackground(color);
			sheet.addCell(new Label(col, row, content, cellFormat1));

			book.write();
			book.close();

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void appendSheetValues(String excelpath, String sheetname, ArrayList<String[]> content) {

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			WritableWorkbook book = Workbook.createWorkbook(new File(excelpath), workbook);
			WritableSheet sheet = book.getSheet(sheetname);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setBackground(Colour.LIGHT_GREEN);
			for (int i = 0; i < content.size(); i++) {
				for (int j = 0; j < content.get(0).length; j++) {
					sheet.addCell(new Label(j + 6, i + 2, content.get(i)[j], cellFormat));
				}
			}

			book.write();
			book.close();

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void changeCellColor(String excelpath, String sheetname, int row, int col, Colour colr) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			WritableWorkbook book = Workbook.createWorkbook(new File(excelpath), workbook);
			WritableSheet sheet = book.getSheet(sheetname);
			WritableCellFormat cellFormat1 = new WritableCellFormat();
			cellFormat1.setBackground(colr);

			sheet.addCell(new Label(col, row, sheet.getCell(col, row).getContents(), cellFormat1));

			book.write();
			book.close();

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Colour getCellColor(String excelpath, String sheetname, int row, int col) {
		Colour cellColor = null;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			Sheet sheet = workbook.getSheet(sheetname);

			cellColor = sheet.getCell(col, row).getCellFormat().getFont().getColour();

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cellColor;
	}

	/*
	 * @1@
	 */
	public static void updateSpecifySheetArrayByTCName(String excelpath, String sheetname, String caseName,
			Map<String, ArrayList<String[]>> datamap, int runcurrent) {
		boolean findrow = false;
		int beginrownumber = 0;
		int endrownumber = 0;

		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelpath));
			WritableWorkbook book = Workbook.createWorkbook(new File(excelpath), workbook);
			WritableSheet sheet = book.getSheet(sheetname);
			WritableCellFormat cellFormat1 = new WritableCellFormat();

			// the row index begin with 0
			int rows = sheet.getRows();
			// the column index begin with 0
			int columns = sheet.getColumns();

			for (int rowindex = 1; rowindex < rows; rowindex++) {
				String cellcontent = sheet.getCell(0, rowindex).getContents().toLowerCase().trim();
				if (cellcontent.equalsIgnoreCase(caseName) && rowindex != rows - 1) {
					if (!findrow) {
						beginrownumber = rowindex;
						findrow = true;
					}
				} else if (cellcontent.equalsIgnoreCase(caseName) && rowindex == rows - 1) {
					if (beginrownumber == 0) {
						beginrownumber = rowindex;
						endrownumber = rowindex + 1;
						findrow = true;
					} else {
						endrownumber = rowindex;
					}
					break;
				} else {
					if (findrow) {
						endrownumber = rowindex;
						break;
					}
				}
			}

			if (findrow) {
				for (int runindex = 0; runindex < runcurrent; runindex++) {
					for (int columnindex = NECESSARY_COLUMN_NUM; columnindex < columns; columnindex++) {
						String mapheader = sheet.getCell(columnindex, beginrownumber - 1).getContents().trim();
						// List<String[]> content = new ArrayList<String[]>();
						if (!mapheader.equals("")) {
							if (endrownumber == rows - 1) {
								sheet.addCell(new Label(columnindex, beginrownumber + runindex,
										datamap.get(mapheader).get(runindex)[0], cellFormat1));
							} else {
								sheet.addCell(new Label(columnindex, beginrownumber + runindex,
										datamap.get(mapheader).get(runindex)[0], cellFormat1));
							}
						} else {
							break;
						}
					}
				}
			}
			book.write();
			book.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	public static void appendCellValues(String excelpath, String sheetname, String testcasename, String cellname,
			String cellvalue) {
		Workbook workbook;
		int rowIndex = 0;
		int colIndex = 0;
		try {
			workbook = Workbook.getWorkbook(new File(excelpath));
			WritableWorkbook book = Workbook.createWorkbook(new File(excelpath), workbook);
			WritableSheet sheet = book.getSheet(sheetname);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

			// the row index begin with 0
			int rows = sheet.getRows();
			// the column index begin with 0
			int columns = sheet.getColumns();
			
			for(int i = 1;i<rows;i++){
				String cellcontent = sheet.getCell(0, i).getContents().toLowerCase().trim();
				if (cellcontent.equalsIgnoreCase(testcasename)) {
					rowIndex = i;
					break;
				}
			}
			if(rowIndex!=0)
			for (int j=1;j<columns;j++){
				String cellcontent = sheet.getCell(j, rowIndex-1).getContents().toLowerCase().trim();
				if (cellcontent.equalsIgnoreCase(cellname)) {
					colIndex = j;
					break;
				}
			}
            if(colIndex!=0)
			sheet.addCell(new Label(colIndex,rowIndex , cellvalue, cellFormat));
			book.write();
			book.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
