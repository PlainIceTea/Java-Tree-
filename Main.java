package Fix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MekanikDirSys list = new MekanikDirSys();
        Scanner input = new Scanner(System.in);
        while (true) {
            String inputString = input.nextLine();
            String[] teks = inputString.split(" ");
            String command = teks[0];
            String nama = String.join(" ", Arrays.copyOfRange(teks, 1, teks.length));
            if (command.equals("mkdir")) {
                list.mkdir(nama);
            } else if (command.equals("touch")) {
                list.touch(nama);
            } else if (command.equals("cd")) {
                list.cd(nama);
            } else if (command.equals("ls")) {
                list.ls();
            } else if (command.equals("rm")) {
                list.rm(nama);
            } else if (command.equals("tree")) {
                System.out.println("root");
                list.tree(list.root);
            } else if (command.equals("exit")) {
                break;
            } else {

            }
        }

    }
}

class TreeNode<T> {

    T data;
    boolean folder;
    TreeNode<T> parent;
    LinkedList<TreeNode<T>> children;
    TreeNode next;

    public TreeNode(T data, boolean value) {
        folder = value;
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.parent = null;
    }

}

class MekanikDirSys<T> {
    TreeNode root;
    TreeNode pointer;
    int level = 1;
    LinkedList<String> sll = new LinkedList<String>();

    public MekanikDirSys() {
        root = new TreeNode("Root", true);
        pointer = root;
    }

    void mkdir(T namaFolder) {

        for (TreeNode<T> elemen : (LinkedList<TreeNode<T>>) pointer.children) {
            if (elemen.data.equals(namaFolder)) {
                System.out.println("folder already exist");
                return;
            }
        }
        TreeNode newFolder = new TreeNode(namaFolder, true);
        newFolder.parent = pointer;
        pointer.children.add(newFolder);

    }

    void touch(T namaFile) {

        for (TreeNode<T> elemen : (LinkedList<TreeNode<T>>) pointer.children) {
            if (elemen.data.equals(namaFile)) {
                System.out.println("file already exist");
                return;
            }
        }

        TreeNode newFile = new TreeNode(namaFile, false);
        newFile.parent = pointer;
        pointer.children.add(newFile);

    }

    void cd(T namaFile) {
        String x = namaFile.toString();
        if (x.equalsIgnoreCase("..") && pointer.parent == null) {
            return;
        } else if (x.equalsIgnoreCase("..") && pointer.parent != null) {
            pointer = pointer.parent;
            return;
        }
        for (TreeNode<T> elemen : (LinkedList<TreeNode<T>>) pointer.children) {
            if (elemen.data.equals(namaFile) && elemen.folder == true) {
                pointer = elemen;
                return;
            }
        }
        System.out.println("folder not found");

    }

    void ls() {
        PriorityQueue<T> printQueue = new PriorityQueue<>();
        for (TreeNode<T> elemen : (LinkedList<TreeNode<T>>) pointer.children) {
            printQueue.add(elemen.data);

        }
        boolean check = false;
        String x = "";
        while (!printQueue.isEmpty()) {
            x += printQueue.poll() + " ";
            check = true;
        }
        if (check == true) {
            System.out.println(x);
        } else {
            return;
        }
    }

    void rm(T namaFile) {
        TreeNode delete = new TreeNode(null, false);
        for (TreeNode<T> elemen : (LinkedList<TreeNode<T>>) pointer.children) {
            if (elemen.data.equals(namaFile)) {
                delete = elemen;

            }
        }
        if (delete.data == null) {
            System.out.println("target not found");
            return;
        }
        pointer.children.remove(delete);
    }

    void tree(TreeNode cetak) {
        TreeNode tmp = cetak;

        for (TreeNode<T> elemen : (LinkedList<TreeNode<T>>) tmp.children) {
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.println("-- " + elemen.data);
            if (elemen.folder == true && elemen.children != null) {
                level++;
                tree(elemen);

            }
        }
        if (level > 1) {
            level--;
        }

    }

}