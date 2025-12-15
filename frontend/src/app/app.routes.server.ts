import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'consulta',
    renderMode: RenderMode.Prerender
  }
];
