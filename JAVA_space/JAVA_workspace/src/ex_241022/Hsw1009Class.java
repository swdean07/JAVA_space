package ex_241022;

class Sports {
    private String name;
    private String[] countries;   
    private String[] equipment;   


    public Sports(String name, String[] countries, String[] equipment) {
        this.name = name;
        this.countries = countries;
        this.equipment = equipment;
    }

    public void introduce() {
        System.out.println("스포츠 이름: " + name);

        System.out.print("스포츠 인기 있는 나라들: ");
        for (int i = 0; i < countries.length; i++) {
            System.out.print(countries[i]);
            if (i < countries.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        System.out.print("필요한 장비: ");
        for (int i = 0; i < equipment.length; i++) {
            System.out.print(equipment[i]);
            if (i < equipment.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}