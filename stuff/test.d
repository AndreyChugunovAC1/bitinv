import core.thread.fiber;
import std.stdio;

void nextFib() {
  ulong[2] a = [1, 0];
  size_t ind;

  while (true) {
    writeln(a[ind]);
    a[ind = 1 - ind] = a[0] + a[1];
    Fiber.yield();
  }
}

void f_short(lazy int a) {
  writeln(typeid(a));
  writeln(a);
}

void f_long(int delegate() a) {
  writeln(typeid(a));
  writeln(a());
}

void main(string[] args) {
  auto fiber = new Fiber({ nextFib(); });

  fiber.call();
  f_short(1);
  f_long(() => 1);
}
