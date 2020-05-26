package cn.tycoding.constant;

/**
 * @author fdd
 * @description: excel有关的常量
 */
public class ExcelConstant {

    /**
     * 列的宽度
     */
    public static final int COLUMN_WIDTH = 18 * 256;

    //表示导入导出的字段只选择数据库表中的index列，从0开始

    /**
     * 实验机器表 machine
     */
    public static final int[] MACHINE_INDEX = {1,2,3,4,5};

    /**
     * 管理员信息 admin
     */
    public static final int[] ADMIN_INDEX = {1,2,3};

    /**
     * 实验者信息 subject
     */
    public static final int[] SUBJECT_INDEX = {1,2,3,4,5,6,7};
    
    /**
     * 文献信息 files_literature
     */
    public static final int[] LITERATURE_INDEX = {2,1,3,4,5,6};

    /**
     * 肌电传感器 egcontrast
     */
    public static final int[] EGCONTRAST_INDEX = {0,1,2,3,4,5,6,7,8,9,
                                                    10,11,12,13,14,15,16,17,18,19,
                                                    20,21,22,23,24,25,26,27,28,29,
                                                    30,31,32};

    /**
     * 前提实验条件 preec
     */
    public static final int[] PREEC_INDEX = {0,13,14,15,16,17,18,19,2,6,7,8,9,10,11,4,5};

}
