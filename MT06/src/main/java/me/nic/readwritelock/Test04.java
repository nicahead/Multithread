package me.nic.readwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 的读写互斥
 * 一个线程获得读锁时,写线程等待; 一个线程获得写锁时,其他线程等待
 */
public class Test04 {
    static class Service {

        // 先定义读写锁
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        // 获得读锁
        Lock readLock = readWriteLock.readLock();
        // 获得写锁
        Lock writeLock = readWriteLock.writeLock();

        // 定义方法读取数据
        public void read() {
            try {
                // 获得读锁
                readLock.lock();
                System.out.println(Thread.currentThread().getName() + "获得读锁，开始读取数据" + System.currentTimeMillis());
                // 模拟读取数据用时
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "读取数据完毕" + System.currentTimeMillis());
                readLock.unlock();
            }
        }

        // 定义方法修改数据
        public void write() {
            try {
                // 获得写锁
                writeLock.lock();
                System.out.println(Thread.currentThread().getName() + "获得写锁，开始修改数据" + System.currentTimeMillis());
                // 模拟读取数据用时
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "修改数据完毕" + System.currentTimeMillis());
                writeLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Service service = new Service();
        // 定义一个线程读数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.read();
            }
        }).start();
        // 定义一个线程写数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                service.write();
            }
        }).start();
    }
}
