
{ pkgs ? import <nixpkgs> {} }:
  pkgs.mkShell {
    nativeBuildInputs = [
      pkgs.babashka
      pkgs.clojure
      pkgs.clojure-lsp
      pkgs.clj-kondo
    ];
}
