{{- define "medical-center-api.name" -}}
{{- if .Values.nameOverride }}
{{- .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- "medical-center-api" }}
{{- end }}
{{- end }}

{{- define "medical-center-api.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name (include "medical-center-api.name" .) | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}

