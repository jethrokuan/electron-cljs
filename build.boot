(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.8.40"]
                 [adzerk/boot-reload "0.4.6"]
                 [adzerk/boot-cljs-repl   "0.3.0"]
                 [adzerk/boot-cljs "1.7.228-1" :scope "test"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel                  "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]])

(deftask prod []
  "Generate production build"
  (comp (cljs :id #{"main"}
              :optimizations :simple)
        (cljs :id #{"renderer"}
              :optimizations :advanced))
  (target))

(deftask dev []
  (comp
   (speak)
   (cljs-repl :ids #{"renderer"})
   (watch)
   (reload :ids #{"renderer"}
           :on-jsload 'app.renderer/init)
   (cljs :ids #{"renderer"})
   (cljs :ids #{"main"}
         :compiler-options {:asset-path "target/main.out"
                            :closure-defines {'app.main/dev? true}})
   (target)))
