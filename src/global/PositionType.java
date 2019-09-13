package global;

import chessPostionInfo.Position;

import java.util.HashMap;

public class PositionType {

    final static private int NORMAL_POSITION = 0; // 只能走一步的非行营位置
    final static private int RAILWAY_POSITION = 1; // 铁道
    final static private int CAMP_POSITION = 2; // 行营
    final static private int FLAG_POSITION = 3; // 大本营

    final static private HashMap<String, Integer> typeMap = new HashMap<String, Integer>() {{
        // 不能用自定义类型Position作key，可以先把坐标Position转换成字符串
        // 第一列
        typeMap.put("0,0", NORMAL_POSITION);
        typeMap.put("0,1", RAILWAY_POSITION);
        typeMap.put("0,2", RAILWAY_POSITION);
        typeMap.put("0,3", RAILWAY_POSITION);
        typeMap.put("0,4", RAILWAY_POSITION);
        typeMap.put("0,5", RAILWAY_POSITION);
        typeMap.put("0,6", RAILWAY_POSITION);
        typeMap.put("0,7", RAILWAY_POSITION);
        typeMap.put("0,8", RAILWAY_POSITION);
        typeMap.put("0,9", RAILWAY_POSITION);
        typeMap.put("0,10", RAILWAY_POSITION);
        typeMap.put("0,11", NORMAL_POSITION);
        // 第二列
        typeMap.put("1,0", FLAG_POSITION);
        typeMap.put("1,1", RAILWAY_POSITION);
        typeMap.put("1,2", CAMP_POSITION);
        typeMap.put("1,3", NORMAL_POSITION);
        typeMap.put("1,4", CAMP_POSITION);
        typeMap.put("1,5", RAILWAY_POSITION);
        typeMap.put("1,6", RAILWAY_POSITION);
        typeMap.put("1,7", CAMP_POSITION);
        typeMap.put("1,8", NORMAL_POSITION);
        typeMap.put("1,9", CAMP_POSITION);
        typeMap.put("1,10", RAILWAY_POSITION);
        typeMap.put("1,11", FLAG_POSITION);
        // 第二列
        typeMap.put("2,0", NORMAL_POSITION);
        typeMap.put("2,1", RAILWAY_POSITION);
        typeMap.put("2,2", NORMAL_POSITION);
        typeMap.put("2,3", CAMP_POSITION);
        typeMap.put("2,4", NORMAL_POSITION);
        typeMap.put("2,5", RAILWAY_POSITION);
        typeMap.put("2,6", RAILWAY_POSITION);
        typeMap.put("2,7", NORMAL_POSITION);
        typeMap.put("2,8", CAMP_POSITION);
        typeMap.put("2,9", NORMAL_POSITION);
        typeMap.put("2,10", RAILWAY_POSITION);
        typeMap.put("2,11", NORMAL_POSITION);
        // 第四列
        typeMap.put("3,0", FLAG_POSITION);
        typeMap.put("3,1", RAILWAY_POSITION);
        typeMap.put("3,2", CAMP_POSITION);
        typeMap.put("3,3", NORMAL_POSITION);
        typeMap.put("3,4", CAMP_POSITION);
        typeMap.put("3,5", RAILWAY_POSITION);
        typeMap.put("3,6", RAILWAY_POSITION);
        typeMap.put("3,7", CAMP_POSITION);
        typeMap.put("3,8", NORMAL_POSITION);
        typeMap.put("3,9", CAMP_POSITION);
        typeMap.put("3,10", RAILWAY_POSITION);
        typeMap.put("3,11", FLAG_POSITION);
        // 第五列
        typeMap.put("4,0", NORMAL_POSITION);
        typeMap.put("4,1", RAILWAY_POSITION);
        typeMap.put("4,2", RAILWAY_POSITION);
        typeMap.put("4,3", RAILWAY_POSITION);
        typeMap.put("4,4", RAILWAY_POSITION);
        typeMap.put("4,5", RAILWAY_POSITION);
        typeMap.put("4,6", RAILWAY_POSITION);
        typeMap.put("4,7", RAILWAY_POSITION);
        typeMap.put("4,8", RAILWAY_POSITION);
        typeMap.put("4,9", RAILWAY_POSITION);
        typeMap.put("4,10", RAILWAY_POSITION);
        typeMap.put("4,11", NORMAL_POSITION);
    }};

    static public int getType(Position position) {
        String key = position.getX() + "," + position.getY();
        return typeMap.get(key);
    }
}
