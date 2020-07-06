package example.java;

/**
 * @author 杜科
 * @description 测试一个对象大小
 * @contact AllenDuke@163.com
 * @date 2020/3/16
 */
public class User {
    int id;
    String name;
}

/**
 * 64位下，关闭指针压缩 共32Byte
 * markword   64bit    8Byte
 * KClass指针          8Byte
 * id     int类型      4Byte
 * name String引用类型 8Byte
 * pending 使其%8==0  4Byte
 */

/**
 * 64位下，开启指针压缩 共24Byte
 * markword   64bit    8Byte
 * KClass指针          4Byte
 * id     int类型      4Byte
 * name String引用类型 4Byte
 * pending 使其%8==0  4Byte
 */

/**
 *指针呀压缩原理
 * 8字节对齐，那么后三位均为000
 * 如果使用16字节对齐，那么压缩后能够指向的容量增大一倍且引用还是占4Byte
 */