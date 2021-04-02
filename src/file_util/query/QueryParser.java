package file_util.query;

import program_util.conditions.*;
import table.Table;
import table.TableList;
import table.exceptions.TableNotFoundException;

import java.util.*;

public class QueryParser {

    private enum ConditionBlockType {
        AND,
        OR
    }

    public static ArrayList<String> parseQuery(String s) throws QueryException, TableNotFoundException,
            InvalidConditionException {
        // Parse SELECT
        int indexSelect = s.indexOf("SELECT");
        if (indexSelect == -1) {
            throw new QueryException(QueryException.QueryError.MISSING_SELECT_STATEMENT);
        }
        int indexFrom = s.indexOf("FROM");
        if (indexFrom == -1) {
            throw new QueryException((QueryException.QueryError.MISSING_FROM_STATEMENT));
        }
        ArrayList<String> selectColumns = parseSelect(s.substring(indexSelect+7, indexFrom-1));
        // Parse FROM
        // Currently, we can only select from one Table, however it could be the case that
        // we can extend the program to do a cartesian product of multiple tables in the future
        // if multiple tables are specified, only the first one will be used.
        int indexWhere = s.indexOf("WHERE");
        if (indexWhere == -1) {
            throw new QueryException((QueryException.QueryError.MISSING_WHERE_STATEMENT));
        }
        Table table = parseFrom(s.substring(indexFrom+5, indexWhere-1));
        if (selectColumns == null) {
            selectColumns = table.getDefaultColumnOrder();
        }
        // Update this if additional clauses added to truncate the parseWhere string
        ConditionBlock conditionBlock = parseWhere(s.substring(indexWhere+6));
        ArrayList<ArrayList<String>> queryResults = table.getTableRows(selectColumns, conditionBlock);
        ArrayList<String> rowResults = new ArrayList<>();
        for (ArrayList<String> row : queryResults) {
            String rowString = "";
            for (int i = 0; i < selectColumns.size(); i++) {
                rowString = rowString + selectColumns.get(i) + " " + "=" + " " + "\"" + row.get(i) + "\" ";
            }
            rowString = rowString.substring(0, rowString.length() - 1);
            rowResults.add(rowString);
        }
        return rowResults;
    }

    private static ArrayList<String> parseSelect(String s) {
        if (s.equals("*")) {
            return null;
        }
        ArrayList<String> selectColumns = new ArrayList<>();
        int previousSeparator = 0;
        int indexOfNextSeparator = s.indexOf(",");
        while (indexOfNextSeparator != -1) {
            selectColumns.add(s.substring(previousSeparator, indexOfNextSeparator));
            previousSeparator = indexOfNextSeparator + 1;
            indexOfNextSeparator = s.indexOf(",", indexOfNextSeparator + 1);
        }
        selectColumns.add(s.substring(previousSeparator));
        return selectColumns;
    }

    private static Table parseFrom(String s) throws TableNotFoundException {
        return TableList.getTableList().getTable(s);
    }

    public static ConditionBlock parseWhere(String s) throws QueryException, InvalidConditionException {
        return parseConditionBlock(s);
    }

    public static ConditionBlock parseConditionBlock(String s) throws QueryException, InvalidConditionException {
        if (s.equals("(*)")) {
            return new AndBlock(new ArrayList<>());
        }
        if (s.length() < 5) {
            throw new QueryException(QueryException.QueryError.ERROR_PARSING_WHERE);
        }
        String condition = s.substring(1, 4);
        if (condition.contains("GT")) { return singleCondition(s, SingleCondition.Where.GT); }
        if (condition.contains("LT")) { return singleCondition(s, SingleCondition.Where.LT); }
        if (condition.contains("GEQ")) { return singleCondition(s, SingleCondition.Where.GEQ); }
        if (condition.contains("LEQ")) { return singleCondition(s, SingleCondition.Where.LEQ); }
        if (condition.contains("NEQ")) { return singleCondition(s, SingleCondition.Where.NEQ); }
        if (condition.contains("EQ")) { return singleCondition(s, SingleCondition.Where.EQ); }
        if (condition.contains("IN")) { return singleCondition(s, SingleCondition.Where.IN); }
        if (condition.contains("OR")) { return andOrCondition(s, ConditionBlockType.OR); }
        if (condition.contains("AND")) { return andOrCondition(s, ConditionBlockType.AND); }
        throw new QueryException(QueryException.QueryError.ERROR_PARSING_WHERE);
    }

    // We expect these to be in the form of (GT ColumnName ValueToCompare)
    // For condition IN only: (IN ColumnName item1,item2,item3,...)
    public static ConditionBlock singleCondition(String s, SingleCondition.Where condition) throws QueryException, InvalidConditionException {
        int columnNameIndex = s.indexOf(" ");
        int valueToCompareIndex = s.indexOf(" ", columnNameIndex + 1);
        if ((columnNameIndex == -1) || (valueToCompareIndex == -1) ||
                ((s.length() - valueToCompareIndex) <= 2) || (s.indexOf("(") != 0) || (s.lastIndexOf(")") != (s.length() - 1))) {
            throw new QueryException(QueryException.QueryError.ERROR_PARSING_WHERE);
        }
        if (condition != SingleCondition.Where.IN) {
            return new SingleCondition(s.substring(columnNameIndex + 1, valueToCompareIndex), condition,
                    s.substring(valueToCompareIndex + 1, s.length() - 1));
        } else {
            Set<String> inSet = new HashSet<>();
            int thisItemPosition = valueToCompareIndex;
            int nextItemPosition = s.indexOf(",");
            do {
                if (nextItemPosition == -1) {
                    inSet.add(s.substring(thisItemPosition + 1, s.length() - 1));
                } else {
                    if (nextItemPosition < thisItemPosition) {
                        throw new QueryException(QueryException.QueryError.ERROR_PARSING_WHERE);
                    }
                    inSet.add(s.substring(thisItemPosition + 1, nextItemPosition));
                    thisItemPosition = nextItemPosition;
                    nextItemPosition = s.indexOf(",", nextItemPosition + 1);
                }
            } while (nextItemPosition != -1);
            return new SingleCondition(s.substring(columnNameIndex + 1, valueToCompareIndex), condition, inSet);
        }
    }

    public static ConditionBlock andOrCondition(String s, ConditionBlockType cbt) throws QueryException, InvalidConditionException {
        s = s.substring(1, s.length() -1);
        ArrayList<ConditionBlock> conditions = new ArrayList<>();
        Stack<Integer> openingParenStack = new Stack<>();

        int initialAndOrBlockIndex = -1;
        boolean inAndOrBlock = false;

        Integer currentOpeningParenIndex = s.indexOf("(");
        Integer currentClosingParenIndex = s.indexOf(")");

        while (currentClosingParenIndex != -1) {
            if ((currentOpeningParenIndex > currentClosingParenIndex) || (currentOpeningParenIndex == -1)) {
                if (openingParenStack.empty()) {
                    throw new QueryException(QueryException.QueryError.ERROR_PARSING_WHERE);
                }
                int initialBlockIndex = openingParenStack.pop();
                if (inAndOrBlock && (initialBlockIndex == initialAndOrBlockIndex)) {
                    inAndOrBlock = false;
                }
                // If the current block is part of a larger AND/OR block, we will skip it.
                if (!inAndOrBlock) {
                    conditions.add(parseConditionBlock(s.substring(initialBlockIndex, currentClosingParenIndex + 1)));
                }
                currentClosingParenIndex = (s.indexOf(")", currentClosingParenIndex + 1));
            } else {
                if (!inAndOrBlock) {
                    if ((s.length() - currentOpeningParenIndex) < 4) {
                        throw new QueryException(QueryException.QueryError.ERROR_PARSING_WHERE);
                    }
                    String openingCond = s.substring(currentOpeningParenIndex + 1, currentOpeningParenIndex + 4);
                    if (openingCond.contains("AND") || openingCond.contains("OR")) {
                        inAndOrBlock = true;
                        initialAndOrBlockIndex = currentOpeningParenIndex;
                    }
                }
                openingParenStack.push(currentOpeningParenIndex);
                currentOpeningParenIndex = (s.indexOf("(", currentOpeningParenIndex + 1));
            }
        }
        if (cbt == ConditionBlockType.AND) {
            return new AndBlock(conditions);
        } else {
            return new OrBlock(conditions);
        }
    }


}
