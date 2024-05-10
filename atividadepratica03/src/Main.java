import java.util.ArrayList;
import java.util.Scanner;

abstract class Funcionario {
    private String nome;
    private int matricula;

    public Funcionario(String nome, int matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public abstract double calcularSalario();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}

class Gerente extends Funcionario {
    private double salarioBase;
    private double bonusAnual;
    private ArrayList<Funcionario> equipe;

    public Gerente(String nome, int matricula, double salarioBase, double bonusAnual) {
        super(nome, matricula);
        this.salarioBase = salarioBase;
        this.bonusAnual = bonusAnual;
        this.equipe = new ArrayList<>();
    }

    @Override
    public double calcularSalario() {
        return salarioBase + bonusAnual;
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        equipe.add(funcionario);
    }

    public void removerFuncionario(Funcionario funcionario) {
        equipe.remove(funcionario);
    }
}

class Desenvolvedor extends Funcionario {
    private int horasTrabalhadas;
    private double valorHora;

    public Desenvolvedor(String nome, int matricula, int horasTrabalhadas, double valorHora) {
        super(nome, matricula);
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHora = valorHora;
    }

    @Override
    public double calcularSalario() {
        return horasTrabalhadas * valorHora;
    }
}

class Estagiario extends Funcionario {
    private int horasSemana;
    private String supervisor;
    private double salario;

    public Estagiario(String nome, int matricula, int horasSemana, String supervisor, double salario) {
        super(nome, matricula);
        this.horasSemana = horasSemana;
        this.supervisor = supervisor;
        this.salario = salario;
    }

    @Override
    public double calcularSalario() {
        return salario;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    public String getSupervisor() {
        return supervisor;
    }
}

public class Main {
    private static ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void adicionarFuncionario() {
        System.out.println("Adicionar novo funcionário:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        if (nome.matches(".*\\d.*")) {
            System.out.println("Nome inválido. Não pode conter números.");
            return;
        }
        System.out.print("Matrícula: ");
        String matriculaStr = scanner.nextLine();
        int matricula;
        try {
            matricula = Integer.parseInt(matriculaStr);
        } catch (NumberFormatException e) {
            System.out.println("Matrícula inválida. Deve ser um número.");
            return;
        }

        System.out.println("Selecione o tipo de funcionário:");
        System.out.println("1. Gerente");
        System.out.println("2. Desenvolvedor");
        System.out.println("3. Estagiário");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        switch (tipo) {
            case 1:
                funcionarios.add(new Gerente(nome, matricula, 5000, 1000));
                break;
            case 2:
                funcionarios.add(new Desenvolvedor(nome, matricula, 40, 50));
                break;
            case 3:
                funcionarios.add(new Estagiario(nome, matricula, 20, "João", 800));
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    public static void removerFuncionario() {
        System.out.println("Remover funcionário:");
        System.out.print("Digite a matrícula do funcionário a ser removido: ");
        int matricula = scanner.nextInt();
        scanner.nextLine();

        boolean removido = false;
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario funcionario = funcionarios.get(i);
            if (funcionario.getMatricula() == matricula) {
                funcionarios.remove(i);
                removido = true;
                break;
            }
        }

        if (!removido) {
            System.out.println("Funcionário não encontrado!");
        }
    }

    public static void exibirFuncionarios() {
        System.out.println("Lista de funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Matrícula: " + funcionario.getMatricula());
            System.out.println("Salário: R$" + funcionario.calcularSalario());
            if (funcionario instanceof Gerente) {
                System.out.println("Cargo: Gerente");
            } else if (funcionario instanceof Desenvolvedor) {
                System.out.println("Cargo: Desenvolvedor");
            } else if (funcionario instanceof Estagiario) {
                System.out.println("Cargo: Estagiário");
            }
            System.out.println();
        }
    }

    public static void buscarFuncionario() {
        System.out.println("Buscar funcionário:");
        System.out.println("Digite '1' para buscar por nome ou '2' para buscar por matrícula:");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("Digite o nome do funcionário: ");
                String nome = scanner.nextLine();
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getNome().equalsIgnoreCase(nome)) {
                        exibirDadosFuncionario(funcionario);
                        return;
                    }
                }
                System.out.println("Funcionário não encontrado!");
                break;
            case 2:
                System.out.print("Digite a matrícula do funcionário: ");
                int matricula = scanner.nextInt();
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getMatricula() == matricula) {
                        exibirDadosFuncionario(funcionario);
                        return;
                    }
                }
                System.out.println("Funcionário não encontrado!");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void exibirDadosFuncionario(Funcionario funcionario) {
        System.out.println("Funcionário encontrado:");
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Matrícula: " + funcionario.getMatricula());
        System.out.println("Salário: R$" + funcionario.calcularSalario());
        System.out.println();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Funcionários ===");
            System.out.println("Selecione a opção desejada:");
            System.out.println("1. Adicionar novo funcionário");
            System.out.println("2. Remover funcionário existente");
            System.out.println("3. Exibir todos os funcionários");
            System.out.println("4. Buscar funcionário");
            System.out.println("5. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarFuncionario();
                    break;
                case 2:
                    removerFuncionario();
                    break;
                case 3:
                    exibirFuncionarios();
                    break;
                case 4:
                    buscarFuncionario();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}