importScripts('workbox-sw.prod.v2.1.2.js');

/**
 * DO NOT EDIT THE FILE MANIFEST ENTRY
 *
 * The method precache() does the following:
 * 1. Cache URLs in the manifest to a local cache.
 * 2. When a network request is made for any of these URLs the response
 *    will ALWAYS comes from the cache, NEVER the network.
 * 3. When the service worker changes ONLY assets with a revision change are
 *    updated, old cache entries are left as is.
 *
 * By changing the file manifest manually, your users may end up not receiving
 * new versions of files because the revision hasn't changed.
 *
 * Please use workbox-build or some other tool / approach to generate the file
 * manifest which accounts for changes to local files and update the revision
 * accordingly.
 */
const fileManifest = [
  {
    "url": "app/global.68bdd333a9d26dca0299.bundle.js",
    "revision": "6b3d07bfff3a696b6e7fbcc1533cbd2a"
  },
  {
    "url": "app/main.68bdd333a9d26dca0299.bundle.js",
    "revision": "59898a36b914f5930c98239a681a03d5"
  },
  {
    "url": "app/manifest.68bdd333a9d26dca0299.bundle.js",
    "revision": "49edbd2fa995f93f7dc0a828fd23c935"
  },
  {
    "url": "app/polyfills.68bdd333a9d26dca0299.bundle.js",
    "revision": "dcb7cba11c9c6c7798ad92c4d7a09496"
  },
  {
    "url": "app/vendor.68bdd333a9d26dca0299.bundle.js",
    "revision": "1cc68f5736807bc56c3ec5d4fe5c4738"
  },
  {
    "url": "content/02a5751109be37af87fc032e6d914c6e.png",
    "revision": "02a5751109be37af87fc032e6d914c6e"
  },
  {
    "url": "content/1cd3a1d782e85ba37677c1a2099bc002.png",
    "revision": "1cd3a1d782e85ba37677c1a2099bc002"
  },
  {
    "url": "content/4a7c3867160db1c254418031cf8dee7f.png",
    "revision": "4a7c3867160db1c254418031cf8dee7f"
  },
  {
    "url": "content/912ec66d7572ff821749319396470bde.svg",
    "revision": "912ec66d7572ff821749319396470bde"
  },
  {
    "url": "content/a30deb26b4eb1521433021e326cbcc2c.png",
    "revision": "a30deb26b4eb1521433021e326cbcc2c"
  },
  {
    "url": "content/ca854e6d0785ba4b9d715049c0bdbcb3.png",
    "revision": "ca854e6d0785ba4b9d715049c0bdbcb3"
  },
  {
    "url": "content/ec1acf8104ffc53a3c3a4d298d27676a.png",
    "revision": "ec1acf8104ffc53a3c3a4d298d27676a"
  },
  {
    "url": "global.68bdd333a9d26dca0299.css",
    "revision": "109a35805515e2af848fa7c7bb3fcded"
  },
  {
    "url": "index.html",
    "revision": "70c5a7bc86b874c40b6617fc2985fa1e"
  },
  {
    "url": "main.68bdd333a9d26dca0299.css",
    "revision": "3d2eab5e74d243180946426db698cba7"
  },
  {
    "url": "swagger-ui/dist/css/print.css",
    "revision": "8cc912f865f2a9260a466a763cb4f325"
  },
  {
    "url": "swagger-ui/dist/css/reset.css",
    "revision": "9e41060781703b7b6492b418708f2ef3"
  },
  {
    "url": "swagger-ui/dist/css/screen.css",
    "revision": "421e5f1e932e9492960bf5ee003a2bf5"
  },
  {
    "url": "swagger-ui/dist/css/style.css",
    "revision": "58f2be3ec002df70ee20e331f9f820b0"
  },
  {
    "url": "swagger-ui/dist/css/typography.css",
    "revision": "d41d8cd98f00b204e9800998ecf8427e"
  },
  {
    "url": "swagger-ui/dist/images/throbber.gif",
    "revision": "bfefe70e3951f1883a84e7bc4033fe97"
  },
  {
    "url": "swagger-ui/index.html",
    "revision": "43be0165f0293266b1cb85f65c8705ea"
  }
];

const workboxSW = new self.WorkboxSW({
  "skipWaiting": true,
  "clientsClaim": true
});
workboxSW.precache(fileManifest);
