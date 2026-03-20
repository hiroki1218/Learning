# Java基礎学習6

# オブジェクト指向について
**データ**とそれに対する処理を **「オブジェクト(モノ)」**として、
ひとまとめにしそれらを組み合わせてプログラムを構築する開発手法。

オブジェクト指向のポイント!!
- **カプセル化**
- **継承**
- **ポリモーフィズム**

# **クラスとインスタンスの関係**
**クラス**とはオブジェクトを生成する設計図又はひな型。
**インスタンス**とはクラスから具体的に作成されたオブジェクトの実体。

オブジェクトとインスタンスは基本的に同義だが厳密には異なります。
**オブジェクト**は「メモリ上に存在する実体そのもの」を指す言葉。
**インスタンス**は「クラスから生成されたもの」という関係性を強調する言葉。

# コンストラクタと`this`
**コンストラク**はクラスから生成したインスタンスのフィールドの初期値を設定する構文。
**`this`**とはインスタンスのフィールドであることを明示していことを表します。
そのためコンストラクタを記述する際に`this`を明示を失念すると
**引数に引数を代入することとなりフィールドには何も入りません。**その結果、
フィールドは初期値のままとなります。

#　コンストラクタ基本構文
```
クラス名(型　フィールド名) {
  this.フィールド名 = フィールド名;
}
```

# カプセル化の本質(private + バリデーション + 実装の隠蔽)
**カプセル化**とはデータ自体とデータを操作するメソッドの内部的な実装を外部から
隠蔽することを指します。
その手段としてはアクセス修飾子(public/private等)を制限したり、
バリデーション(入力チェック)を入れることで、データの整合性を守れます。

# 継承(extends/super/オーバーライド)
**継承**とは`extends`を使用することで親クラスの機能(フィールドやメソッド等)を子クラスに**拡張**し子クラス独自の機能を追加することが出来ます。
`super`は親クラスのコンストラクタを呼び出しを意味します。

# **継承の基本構文**
```
class クラス名(子クラス)　extends クラス名(親クラス)　{

  'コンストラクタ
  super(型　フィールド名);
  this.フィールド名 = フィールド名;

}

```

# インターフェースと抽象化
## まず大前提：この2つは「目的」が違う
| | インターフェース | 抽象クラス |
|---|---|---|
| 一言で言うと | **「何ができるか」のルール** | **「何者であるか」の共通設計図** |
| 日常の例え | 資格・免許 | 親の血筋 |
| 持てる数 | 複数OK | 1つだけ |
| 共通処理 | 持てない（ルールだけ） | 持てる |

## 判断基準はこの1つだけ
 
**「共通の処理（コード）を持たせたいか？」**
- **NO → インターフェース**（ルールだけ決めればいい）
- **YES → 抽象クラス**（共通コードを親にまとめたい）

```java
// 「口座」という資格のルール
interface Account {
    void deposit(int amount);   // 入金できること
    int getBalance();           // 残高を返せること
    void showStatus();          // 状態を表示できること
}
```
 
ルールだけなので、実装は各クラスが自由にやる。
 
```java
class SavingsAccount implements Account {
    public void deposit(int amount) {
        if (amount > 0) this.balance += amount;  // 普通に入金
    }
}
 
class FixedDeposit implements Account {
    public void deposit(int amount) {
        System.out.println("追加入金不可");       // 断る
    }
}
```
 
**ポイント：同じルール（deposit）に対して、やり方が全く違ってもいい。**
 
資格は複数持てる。
 
```java
class Person implements Driver, Cook, Swimmer { }
```
 
---
 
## 抽象クラス = 親の設計図
 
「口座である以上、ここは全員同じ動きをする」という共通処理を親にまとめる。
ただし一部は子クラスごとに動きが変わるので、枠だけ用意して中身は任せる。
 
```java
abstract class Account {
    String ownerName;
    int balance;
 
    // ここは全員同じ → 親に書いてしまう
    public int getBalance() {
        return this.balance;
    }
 
    // ここは子クラスごとに違う → 枠だけ用意
    abstract void deposit(int amount);
    abstract void showStatus();
}
```
 
子クラスは共通処理を引き継ぎつつ、違う部分だけ書く。
 
```java
class SavingsAccount extends Account {
    // getBalance()は書かなくていい（親から引き継ぐ）
 
    void deposit(int amount) {
        if (amount > 0) this.balance += amount;
    }
 
    void showStatus() {
        System.out.println(ownerName + " | 残高：" + balance + "円");
    }
}
```
 
**ポイント：共通コードの重複をなくせる。ただし親は1つだけ。**
 
```java
class SavingsAccount extends Account { }       // OK
class SavingsAccount extends Account, Loan { } // NG（親は1つだけ）
```
 
---
 
## ごっちゃになる原因
 
どちらも「中身を書かないメソッドがある」から似て見える。
 
```java
// インターフェース
interface Account {
    void deposit(int amount);      // 中身なし
}
 
// 抽象クラス
abstract class Account {
    abstract void deposit(int amount);  // 中身なし
}
```
 
この部分だけ見ると確かに同じに見える。
**違いは「共通の中身を持てるかどうか」**。ここだけ見れば混同しない。
 
- インターフェース → ルールだけ。共通コードは置けない。
- 抽象クラス → ルールも書けるし、共通コードも置ける。
 
---
 
## 実務ではどう使い分けるか
 
### インターフェースを使う場面
- 「何ができるか」だけ統一したい
- 複数の役割を1つのクラスに持たせたい
- 全く関係ないクラス同士を同じように扱いたい
 
```java
// 「表示できるもの」というルール
interface Displayable {
    void display();
}
 
// 全く違うクラスが同じルールを持てる
class Receipt implements Displayable { ... }
class Report implements Displayable { ... }
class Chart implements Displayable { ... }
```
 
### 抽象クラスを使う場面
- 共通の処理やデータがある
- 「同じ種類のもの」の間で共通部分をまとめたい
 
```java
// 口座は全て名義と残高を持つ → 共通データを親にまとめる
abstract class Account {
    String ownerName;
    int balance;
    public int getBalance() { return this.balance; }
}
```
 
---
 
## 迷ったときのフローチャート
 
```
共通の処理（コード）を親にまとめたい？
  │
  ├─ YES → 抽象クラス（extends）
  │
  └─ NO → インターフェース（implements）
```
 
さらに言えば、**迷ったらインターフェースを選ぶ**のが現代Javaの基本方針。
理由：後から抽象クラスに変えるのは難しいが、インターフェースは柔軟に追加できるから。


# ポリモーフィズム

**「同じ命令を出しても、相手によって動きが変わる」** こと。
 
---
 
## 日常の例：リモコンの電源ボタン
 
「電源ON」という同じ操作をする。
 
- テレビに向ける → テレビがつく
- エアコンに向ける → エアコンがつく
- 照明に向ける → 照明がつく
 
押す側は相手の内部構造を知らない。ただ「電源ON」と命令するだけ。
相手が何であろうと同じ操作で済む。これがポリモーフィズム。
 
---
 
## コードの例：銀行口座
 
「入金する」という同じ命令を出す。
 
```java
account.deposit(1000);
// 相手がSavingsAccount → 残高が増える
// 相手がFixedDeposit   → 「追加入金不可」と断られる
```
 
リモコンの例と構造は同じ。
 
| リモコンの例 | コードの例 |
|---|---|
| 電源ON（共通の操作） | deposit（共通のメソッド） |
| テレビ / エアコン / 照明 | SavingsAccount / FixedDeposit |
| 相手ごとに起きることが違う | クラスごとに処理が違う |
| 押す側は中身を知らない | 呼び出す側は実装を知らない |
 
---
 
## ポリモーフィズムがないと何が困るか
 
相手が何かを毎回確認して、分岐を書く羽目になる。
 
```java
// 口座の種類が増えるたびにif文が増え続ける
if (account instanceof SavingsAccount) {
    // 普通預金の処理
} else if (account instanceof FixedDeposit) {
    // 定期預金の処理
} else if (account instanceof ForeignCurrency) {
    // 外貨預金の処理
}
// まだ増える...
```
 
ポリモーフィズムがあれば、口座が何種類あってもこれだけで済む。
 
```java
account.deposit(1000);
```
 
---
 
## JVMはどう判断しているか
 
変数の型ではなく、**newで作った実体のアドレスの先を見ている**。
 
```java
Account account = new SavingsAccount("Hiroki", 5000, 100000);
```
 
- **コンパイル時** → 変数の型（Account）で「depositあるな、文法OK」と確認
- **実行時** → アドレスの先の実体（SavingsAccount）を見て、そのクラスのdepositを実行

#　オーバーライドとは
**オーバーライド**とは親クラスのメソッドをサブクラスのメソッドで上書きすること。