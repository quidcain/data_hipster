export interface IDatasource {
  name: string;
  config: IDatasourceConfig;
}

interface IDatasourceConfig {
  connection: string;
  enabled: boolean;
  type: string;
  configProps: object;
  workspaces: IWorkspaceConfig;
}

interface IWorkspaceConfigMap {
  [key: string]: IWorkspaceConfig;
}

interface IWorkspaceConfig {
  location: string;
  writable: boolean;
  defaultInputFormat: string;
  allowAccessOutsideWorkspace: boolean;
}
