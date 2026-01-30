class Area {
    void area(double length, double breadth) {
        System.out.println("Area of Rectangle = " + length * breadth);
    }
    void area(int side) {
        System.out.println("Area of Square = " + side * side);
    }
    public static void main(String[] args) {
        Area obj = new Area();

        obj.area(5);
        obj.area(10, 6);
    }
}
