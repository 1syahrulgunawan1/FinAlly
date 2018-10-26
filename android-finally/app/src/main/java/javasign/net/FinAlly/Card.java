package javasign.net.FinAlly;

public class Card {

    public int id;
    public String key;
    public String nickname;
    public Vendor vendor;
    public Products products;

    public Card(int id, String nickname, Vendor vendor, Products products) {
        this.id = id;
        this.nickname = nickname;
        this.vendor = vendor;
        this.products = products;
    }

    public Card() {

    }

    public static class Vendor {
        public int id;
        public String name;

        public Vendor() {

        }

        public Vendor(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class Products {

        public int id;
        public String number, type, nickname;
        public double balance;
        public Properties properties;

        public static class Properties {

            public String cardholder, credit_limit, available_balance, last_payment, print_bills, due_date, customerNo, statementDate, cashAdvanceLimit;
            public String availableCashAdvanceLimit, cashAdvance, outstandingInstallment, openingBalance, newCharges, closingBalance;
            public double minimum_payment;

            public Properties() {

            }

            public Properties(String cardholder, String credit_limit, String print_bills, String due_date, double minimum_payment) {
                this.cardholder = cardholder;
                this.credit_limit = credit_limit;
                this.print_bills = print_bills;
                this.due_date = due_date;
                this.minimum_payment = minimum_payment;
            }

            public String getCardholder() {
                return cardholder;
            }

            public String getDue_date() {
                return due_date;
            }

            public String getAvailable_balance() {
                return available_balance;
            }

            public String getAvailableCashAdvanceLimit() {
                return availableCashAdvanceLimit;
            }

            public double getMinimum_payment() {
                return minimum_payment;
            }

            public String getCashAdvance() {
                return cashAdvance;
            }

            public String getCashAdvanceLimit() {
                return cashAdvanceLimit;
            }

            public String getClosingBalance() {
                return closingBalance;
            }

            public String getCredit_limit() {
                return credit_limit;
            }

            public String getCustomerNo() {
                return customerNo;
            }

            public String getLast_payment() {
                return last_payment;
            }

            public String getNewCharges() {
                return newCharges;
            }

            public String getOpeningBalance() {
                return openingBalance;
            }

            public String getOutstandingInstallment() {
                return outstandingInstallment;
            }

            public String getStatementDate() {
                return statementDate;
            }
        }

        public Products() {

        }

        public Products(int id, String number, String type, String nickname, double balance, Properties properties) {
            this.id = id;
            this.number = number;
            this.type = type;
            this.nickname = nickname;
            this.balance = balance;
            this.properties = properties;
        }

        public int getId() {
            return id;
        }

        public String getNickname() {
            return nickname;
        }

        public String getType() {
            return type;
        }

        public double getBalance() {
            return balance;
        }

        public String getNumber() {
            return number;
        }

        public Properties getProperties() {
            return properties;
        }
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Products getProducts() {
        return products;
    }

    public Vendor getVendor() {
        return vendor;
    }
}