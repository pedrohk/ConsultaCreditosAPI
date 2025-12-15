import {
  AngularNodeAppEngine,
  createNodeRequestHandler,
  isMainModule,
  writeResponseToNodeResponse,
} from '@angular/ssr/node';
import express from 'express';
import { join, dirname } from 'node:path';
import { fileURLToPath } from 'node:url';

const browserDistFolder = join(dirname(fileURLToPath(import.meta.url)), '../browser');

const app = express();
const angularApp = new AngularNodeAppEngine();

app.use(
  express.static(browserDistFolder, {
    maxAge: '1y',
    index: false,
    redirect: false,
  }),
);

app.use((req, res, next) => {
  angularApp
    .handle(req)
    .then((response) =>
      response ? writeResponseToNodeResponse(response, res) : next(),
    )
    .catch(next);
});

if (isMainModule(import.meta.url)) {
  const port = Number(process.env['PORT'] || 4000);
  const server = app.listen(port, () => {
    console.log(`Node Express server listening on http://localhost:${port}`);
  });
  server.on('error', (err: any) => {
    throw err;
  });
}

export const reqHandler = createNodeRequestHandler(app);
