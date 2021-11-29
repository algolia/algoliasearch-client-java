import type { RequestOptions } from '../utils/types';

export * from './batchObject';
export * from './batchResponse';
export * from './errorBase';
export * from './highlightResult';
export * from './multipleQueries';
export * from './multipleQueriesObject';
export * from './multipleQueriesResponse';
export * from './operation';
export * from './rankingInfo';
export * from './rankingInfoMatchedGeoLocation';
export * from './record';
export * from './saveObjectResponse';
export * from './searchParams';
export * from './searchParamsString';
export * from './searchResponse';
export * from './searchResponseFacetsStats';
export * from './snippetResult';

export interface Authentication {
  /**
   * Apply authentication settings to header and query params.
   */
  applyToRequest(requestOptions: RequestOptions): Promise<void> | void;
}

export class ApiKeyAuth implements Authentication {
  public apiKey: string = '';

  constructor(private location: string, private paramName: string) {}

  applyToRequest(requestOptions: RequestOptions): void {
    if (this.location == 'query') {
      requestOptions.queryParameters[this.paramName] = this.apiKey;
    } else if (this.location == 'header' && requestOptions && requestOptions.headers) {
      requestOptions.headers[this.paramName] = this.apiKey;
    } else if (this.location == 'cookie' && requestOptions && requestOptions.headers) {
      if (requestOptions.headers['Cookie']) {
        requestOptions.headers['Cookie'] +=
          '; ' + this.paramName + '=' + encodeURIComponent(this.apiKey);
      } else {
        requestOptions.headers['Cookie'] = this.paramName + '=' + encodeURIComponent(this.apiKey);
      }
    }
  }
}