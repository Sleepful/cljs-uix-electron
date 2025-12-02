# Uix + Electron starter

## Reframe + Uix

Initial boiler plate starter from [ https://github.com/pitch-io/uix-starter ](uix-starter) by using:

```
npx create-uix-app@latest my-app --re-frame # adds re-frame setup
```

## However

This repo has been expanded to build for Electron by following 
[ thheller/electron-cljs ]( https://github.com/thheller/electron-cljs )

## Development

```shell
npm i # install NPM deps
```

### Browser
```shell
npm i # install NPM deps
npm run dev # run dev build in watch mode with CLJS REPL
```

### Electron
```shell
# in two shells:
npm elc-watch
npm elc-run # or just `npx electron .`
```

## Production
```shell
npm run release # build production bundle
```
